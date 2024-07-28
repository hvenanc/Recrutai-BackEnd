package com.recrutaibackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.Instant;

@Entity
@Table(name = "tb_vacancy")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "work_model")
    private String workModel;

    @Column(name = "avg_salary")
    private Integer avgSalary;

    @Column(name = "positions")
    private Short positions;

    @Column(name = "applications")
    @Generated(event = EventType.INSERT)
    private Integer applications;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recruiter_id")
    private Member recruiter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "published_by")
    private Member publishedBy;

    @CreationTimestamp
    @Column(name = "published_at")
    private Instant publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closed_by")
    private Member closedBy;

    @Column(name = "closed_at")
    private Instant closedAt;

    public Vacancy(
            String title,
            String description,
            String workModel,
            Integer avgSalary,
            Short positions,
            Member recruiter,
            Member publishedBy
    ) {
        this.title = title;
        this.description = description;
        this.workModel = workModel;
        this.avgSalary = avgSalary;
        this.positions = positions;
        this.recruiter = recruiter;
        this.publishedBy = publishedBy;
    }
}
