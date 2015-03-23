package com.theupswell.appengine.counter.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.Test;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.theupswell.appengine.counter.data.CounterData;
import com.theupswell.appengine.counter.data.CounterShardOperationData;
import com.theupswell.appengine.counter.data.CounterShardOperationData.Type;
import com.theupswell.appengine.counter.data.CounterShardData;

/**
 * 
 */
public class ShardedCounterMutationPersistenceTest extends ShardedCounterServiceIncrementTest
{
	@Test
	public void testSaveAndLoadShardedCounterMutationData()
	{
		final Key<CounterData> counterDataKey = CounterData.key(TEST_COUNTER1);
		final Key<CounterShardData> counterShardDataKey = CounterShardData.key(counterDataKey, 0);
		final UUID uuid = UUID.randomUUID();
		final CounterShardOperationData counterShardOperationData = new CounterShardOperationData(
			counterShardDataKey, uuid, Type.INCREMENT, 1L);

		ObjectifyService.ofy().save().entity(counterShardOperationData).now();

		final CounterShardOperationData dsCounterShardOperationData = ObjectifyService.ofy().load()
			.key(counterShardOperationData.getTypedKey()).now();

		assertThat(dsCounterShardOperationData, is(counterShardOperationData));
	}
}
