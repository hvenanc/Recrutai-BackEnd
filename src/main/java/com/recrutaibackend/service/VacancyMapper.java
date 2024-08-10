package com.recrutaibackend.service;

import com.recrutaibackend.dto.VacancyRequest;
import com.recrutaibackend.dto.VacancyResponse;
import com.recrutaibackend.model.Member;
import com.recrutaibackend.model.Vacancy;
import org.springframework.stereotype.Service;

@Service
public class VacancyMapper {

    public Vacancy mapToEntity(VacancyRequest request, Member recruiter, Member publisher) {
        return new Vacancy(
                request.title(),
                request.description(),
                request.workModel(),
                request.avgSalary() * 100, // convert to cents
                request.positions(),
                recruiter,
                publisher
        );
    }

    public VacancyResponse mapToResponse(Vacancy entity) {
        return new VacancyResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getWorkModel(),
                entity.getAvgSalary() / 100, // convert back to original value
                entity.getPositions(),
                entity.getApplications(),
                entity.getRecruiter().getId(),
                entity.getPublishedBy().getId()
        );
    }

}
