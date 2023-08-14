package com.mannavoca.zenga.domain.praise.infrastructure;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Configuration
@Transactional
@RequiredArgsConstructor
public class MemberPraiseDeleteBatchConfig {
    private final MemberPraiseRepository memberPraiseRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deletePraiseJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("deletePraiseJob_"+now)
                .preventRestart()
                .start(deletePraiseStep())
                .build();
    }

    @Bean
    public Step deletePraiseStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("deletePraiseStep_"+now)
                .<MemberPraise, MemberPraise> chunk(10)
                .reader(findAllNotPraisedMemberPraiseReader())
                .processor(assignPraiseToAllMemberProcessor())
                .writer(memberPraisePreemptionWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<MemberPraise> findAllNotPraisedMemberPraiseReader() {
        List<MemberPraise> memberPraiseList = memberPraiseRepository.findAllNotPraised();
        return new ListItemReader<>(memberPraiseList);
    }

    public ItemProcessor<MemberPraise, MemberPraise> assignPraiseToAllMemberProcessor() {
        return new ItemProcessor<MemberPraise, MemberPraise>() {
            @Override
            public MemberPraise process(MemberPraise item) throws Exception {
                return item;
            }
        };
    }

    public ItemWriter<MemberPraise> memberPraisePreemptionWriter() {
        return memberPraiseRepository::deleteAll;
    }

}
