package com.recrutaibackend.vacancy.interview;

import com.recrutaibackend.address.AddressResponse;
import com.recrutaibackend.institution.member.MemberResponse;

import java.time.OffsetDateTime;

public record InterviewResponse(
        long id,
        long applicationId,
        long interviewerId,
        String title,
        String description,
        OffsetDateTime scheduledTo,
        AddressResponse address,
        String reunionURL,
        boolean isRemote,
        long createdById
) {
}
