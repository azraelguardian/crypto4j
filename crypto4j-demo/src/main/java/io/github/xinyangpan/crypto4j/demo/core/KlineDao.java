package io.github.xinyangpan.crypto4j.demo.core;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlineDao extends PagingAndSortingRepository<KlinePo, Long> {

}