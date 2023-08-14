package com.mannavoca.zenga.domain.praise.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepository;
import com.mannavoca.zenga.domain.praise.infrastructure.MemberPraiseCreateBatchConfig;
import com.mannavoca.zenga.domain.praise.infrastructure.MemberPraiseDeleteBatchConfig;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Map;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberPraiseService {
    private final JobLauncher jobLauncher;
    private final MemberPraiseRepository memberPraiseRepository;
    private final MemberPraiseCreateBatchConfig memberPraiseCreateBatchConfig;
    private final MemberPraiseDeleteBatchConfig memberPraiseDeleteBatchConfig;

    @SqsListener(value = "CREATE_PREEMPTION_PRAISE", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void createPreemptionPraise() {
        Map<String, JobParameter> confMap = Map.of("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try{
            jobLauncher.run(memberPraiseCreateBatchConfig.createPraiseJob(), jobParameters);
        } catch (Exception e) {
            log.error("memberPraiseCreateBatchJob error : {}", e.getMessage());
        }
    }

    @SqsListener(value = "DELETE_ORPHAN_PRAISE", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void createPraise() {
        Map<String, JobParameter> confMap = Map.of("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try{
            jobLauncher.run(memberPraiseDeleteBatchConfig.deletePraiseJob(), jobParameters);
        } catch (Exception e) {
            log.error("memberPraiseDeleteBatchJob error : {}", e.getMessage());
        }
    }

}
