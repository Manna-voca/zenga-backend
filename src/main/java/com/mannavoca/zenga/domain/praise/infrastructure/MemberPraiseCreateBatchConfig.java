package com.mannavoca.zenga.domain.praise.infrastructure;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.TimeSectionType;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepository;
import com.mannavoca.zenga.domain.praise.domain.repository.PraiseRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@Transactional
@RequiredArgsConstructor
public class MemberPraiseCreateBatchConfig {
    private final MemberPraiseRepository memberPraiseRepository;
    private final PraiseRepository praiseRepository;
    private final MemberRepository memberRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job createPraiseJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("createPraiseJob_"+now)
                .preventRestart()
                .start(createPraiseStep())
                .build();
    }

    @Bean
    public Step createPraiseStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("createPraiseStep_"+now)
                .<Member, List<MemberPraise>> chunk(10)
                .reader(findAllMemberReader())
                .processor(assignPraiseToAllMemberProcessor())
                .writer(memberPraisePreemptionWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<Member> findAllMemberReader() {
        List<Member> memberList = memberRepository.findAll();
        return new ListItemReader<>(memberList);
    }

    public ItemProcessor<Member, List<MemberPraise>> assignPraiseToAllMemberProcessor() {
        return new ItemProcessor<Member, List<MemberPraise>>() {
            @Override
            public List<MemberPraise> process(Member item) throws Exception {
                return TimeSectionType.getSectionTypes().stream().map(sectionType ->
                        MemberPraise.builder()
                                .praiseMember(item)
                                .praisedMember(null)
                                .timeSection(sectionType)
                                .praise(praiseRepository.findRandomPraise())
                                .build()
                ).collect(Collectors.toList());
            }
        };
    }

    public ItemWriter<List<MemberPraise>> memberPraisePreemptionWriter() {
        return list -> {
            List<MemberPraise> flattenMemberPraise = list.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            memberPraiseRepository.saveAll(flattenMemberPraise);
        };
    }

}
