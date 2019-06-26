package com.icefire.api.information.domain.repository;

import com.icefire.api.information.domain.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {


}
