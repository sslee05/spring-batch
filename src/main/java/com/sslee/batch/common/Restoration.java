package com.sslee.batch.common;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

/**
 * spring batch duplicate exception시 
 * org.springframework.dao.OptimisticLockingFailureException: 
 * Attempt to update step execution id=1062 with wrong version (1), 과같은 에러는 
 * can't controll step flow 제어가 않되는 bug 로 인해 이를 추가 한다.
 * @author sslee
 *
 */
public interface Restoration {
	
	ExitStatus restorate(JobExecution jobExecution);

}
