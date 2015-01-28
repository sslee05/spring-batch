package com.sslee.batch.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.item.ItemWriter;

/**
 * sqlException 시 batch_job_execution table attempt to update step execution id with wrong version  발생
 * 이유는 sqlSessionTemplate ExecutorType 이 BATCH로 되었을경우 발생 
 * before batch_job_execution update after sqlSessionTemplate.flushStatements()를 
 * 실행하기 때문이다. 그래서  attempt to update step execution id with wrong version 이 발생 
 * 이를 방지 하기 위해 TemplateMethod pattern를 이용하여 itemWriter를 구조화 한다.
 * @author sslee(sangseok lee)
 *
 * @param <T>
 */
public abstract class ExecutorBatchItemWriter<T> implements ItemWriter<T> {
	
	private List<SqlSessionTemplate> sqlSessionTemplates;
	
	@Override
	final public void write(List<? extends T> items) throws Exception {
		
		writeItems(items);
		
		for(SqlSessionTemplate sqlSessionTemplate : sqlSessionTemplates) {
			
			if(!sqlSessionTemplate.getExecutorType().equals(ExecutorType.BATCH))
				continue;
			
			sqlSessionTemplate.flushStatements();
		}
	}
	
	public ExecutorBatchItemWriter(List<SqlSessionTemplate> sqlSessionTemplates) {
		this.sqlSessionTemplates = sqlSessionTemplates;
	}
	
	public ExecutorBatchItemWriter(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplates = this.sqlSessionTemplates == null ? 
				new ArrayList<SqlSessionTemplate>() : this.sqlSessionTemplates;
		
        this.sqlSessionTemplates.add(sqlSessionTemplate);
	}
	
	//abstract protected void setSqlSessionTemplates();
	abstract protected void writeItems(List<? extends T> items); 

}
