package com.recrutaibackend.vacancy.interview;

import com.recrutaibackend.institution.member.MemberService;
import com.recrutaibackend.vacancy.application.ApplicationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.OffsetDateTime;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class InterviewService {
    private static final Duration REASONABLE_REMOTE_PREP_TIME = Duration.ofMinutes(30);
    private static final Duration REASONABLE_ON_SITE_PREP_TIME = Duration.ofHours(2);

    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;
    private final MemberService memberService;
    private final ApplicationService applicationService;

    public InterviewService(
            InterviewRepository interviewRepository,
            InterviewMapper interviewMapper,
            MemberService memberService,
            ApplicationService applicationService
    ) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
        this.memberService = memberService;
        this.applicationService = applicationService;
    }

    @Transactional
    public Interview create(InterviewRequest request) {
        validateInterviewSchedule(request);

        var application = applicationService.findById(request.applicationId());
        var interviewer = memberService.findById(request.interviewerId());
        var createdBy = memberService.findById(request.createdById());

        var interview = interviewMapper.mapToEntity(request, application, interviewer, createdBy);

        return interviewRepository.save(interview);
    }

    private void validateInterviewSchedule(InterviewRequest request) {
        if (request.isRemote() && isPrepTimeNotEnough(request.scheduledTo(), REASONABLE_REMOTE_PREP_TIME)) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Remote interviews must be scheduled at least 30 minutes in advance");
        }
        if (!request.isRemote() && isPrepTimeNotEnough(request.scheduledTo(), REASONABLE_ON_SITE_PREP_TIME)) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "On-site interviews must be scheduled at least 2 hours in advance");
        }
    }

    private boolean isPrepTimeNotEnough(OffsetDateTime scheduledTo, Duration prepTime) {
        return scheduledTo.minus(prepTime).isBefore(OffsetDateTime.now());
    }

}
