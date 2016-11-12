package org.arra.tests.interpretter.comp;

import org.arra.interpretter.chunk.ArraValue;
import org.arra.interpretter.chunk.Special;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

/**
 * An test-class to Test the {@link org.arra.interpretter.comp.Stack} class.
 * */
public class Stack{
	
	/**
	 * An instance of the {@link org.arra.interpretter.comp.Stack} class that will
	 * be used for all tests. Obtained via the <i> getInstance() </i> method.
	 * */
	final static org.arra.interpretter.comp.Stack STACK = 
				 org.arra.interpretter.comp.Stack.getInstance();
	
	/**
	 * Used to prepare the {@link #STACK} before each test.
	 * */
	@Before
	public void before(){
		STACK.clearEnviroment();
		STACK.loadEnviroment();
	}
	
	/**
	 * Used to test the 
	 * {@link org.arra.interpretter.comp.Stack#add(ArraValue)}
	 * and
	 * {@link org.arra.interpretter.comp.Stack#add(String,ArraValue)}
	 * methods.
	 * */
	@Test
	public void add(){
		
		//Setup test values
		final ArraValue strval = ArraValue.of("TEST");
		final ArraValue intval = ArraValue.of(123);
		final ArraValue fptval = ArraValue.of(123.3);
		final ArraValue sysnul = Special.SYS_NULL;
		final ArraValue nulval = null;
		
		//Add values to stack 
		STACK.add(strval);
		STACK.add(intval);
		STACK.add(fptval);
		STACK.add(sysnul);
		STACK.add(nulval);
		
		//Add values to variable map
		STACK.add("strval", strval);
		STACK.add("intval",intval);
		STACK.add("fptval",fptval);
		STACK.add("sysnul",sysnul);
		STACK.add("nulval",nulval);
		STACK.add(null,null);
		
		//Test
		assertEquals(STACK.stackSize(),3);
		assertEquals(STACK.mapSize(),10);
		
		assertEquals(STACK.get("[0]"), strval);
		assertEquals(STACK.get("[1]"), intval);
		assertEquals(STACK.get("[2]"), fptval);
		
		assertNull(STACK.get("[3]").toJava());
		assertNull(STACK.get("[4]").toJava());
		
		assertEquals(STACK.get("[strval]"), strval);
		assertEquals(STACK.get("[intval]"), intval);
		assertEquals(STACK.get("[fptval]"), fptval);
		assertEquals(STACK.get("[sysnul]"), sysnul);
		assertEquals(STACK.get("[nulvar]"), sysnul);

	}
	
	/**
	 * Used to test the 
	 * {@link org.arra.interpretter.comp.Stack#get(String)}
	 * method.
	 * */
	@Test
	public void get(){
		
		//Setup test values
		final ArraValue strval = ArraValue.of("TEST");
		final ArraValue intval = ArraValue.of(123);
		final ArraValue fptval = ArraValue.of(123.3);
		final ArraValue sysnul = Special.SYS_NULL;
		final ArraValue nulval = null;
		
		//Add values to stack 
		STACK.add(strval);
		STACK.add(intval);
		STACK.add(fptval);
		STACK.add(sysnul);
		STACK.add(nulval);
		
		//Add values to variable map
		STACK.add("strval", strval);
		STACK.add("intval",intval);
		STACK.add("fptval",fptval);
		STACK.add("sysnul",sysnul);
		STACK.add("nulval",nulval);
		
		//Test
		assertEquals((int) STACK.get("[STACK_SIZE]").toJava(),STACK.stackSize());
		assertEquals((int) STACK.get("[MAP_SIZE]").toJava(), STACK.mapSize());
		assertEquals(STACK.get("[SYS_ARGS]").toJava(),"");
		assertEquals(STACK.get("[SYS_OUT]").toJava(), System.out);
		assertEquals(STACK.get("[SYS_ERR]").toJava(), System.err);
		assertEquals(STACK.get("[SYS_IN]").toJava(), System.in);
		assertEquals(STACK.get("[FUNC_ARGS]").toJava(), "");
		
		assertEquals(STACK.get("[0]"),STACK.get("[strval]"));
		assertEquals(STACK.get("[1]"),STACK.get("[intval]"));
		assertEquals(STACK.get("[2]"),STACK.get("[fptval]"));
		assertEquals(STACK.get("[3]"),STACK.get("[sysval]"));
		assertEquals(STACK.get("[4]"),STACK.get("[nulval]"));
		
		assertEquals(STACK.get("%2"),STACK.get("[strval]"));
		assertEquals(STACK.get("%1"),STACK.get("[intval]"));
		assertEquals(STACK.get("%0"),STACK.get("[fptval]"));
	}

	/**
	 * Used to test the return value of
	 * {@link org.arra.interpretter.comp.Stack#getVariableMap()}
	 * for all needed values.
	 * */
	@Test
	public void getVariableMap(){
		
		//Setup test values
		final ArraValue strval = ArraValue.of("TEST");
		final ArraValue intval = ArraValue.of(123);
		final ArraValue fptval = ArraValue.of(123.3);
		final ArraValue sysnul = Special.SYS_NULL;
		final ArraValue nulval = null;
		
		//Add values to variable map
		STACK.add("strval", strval);
		STACK.add("intval",intval);
		STACK.add("fptval",fptval);
		STACK.add("sysnul",sysnul);
		STACK.add("nulval",nulval);
		
		//Transform to map
		final Map<String,ArraValue> map = STACK.getVariableMap();
		
		//Test
		assertEquals(map.size(),10);
		
		assertTrue(map.containsKey("STACK_SIZE"));
		assertTrue(map.containsKey("MAP_SIZE"));
		assertTrue(map.containsKey("SYS_OUT"));
		assertTrue(map.containsKey("SYS_IN"));
		assertTrue(map.containsKey("SYS_ERR"));
		assertTrue(map.containsKey("SYS_ARGS"));
		assertTrue(map.containsKey("FUNC_ARGS"));
		assertTrue(map.containsKey("strval"));
		assertTrue(map.containsKey("intval"));
		assertTrue(map.containsKey("fptval"));
		
		assertFalse(map.containsKey("sysnul"));
		assertFalse(map.containsKey("nulval"));
		assertFalse(map.containsKey("TEST"));
	}

	/**
	 * Used to test the return value of
	 * {@link org.arra.interpretter.comp.Stack#getVariableList()}
	 * for all needed values.
	 * */
	@Test
	public void getVariableList(){
		
		//Setup test values
		final ArraValue strval = ArraValue.of("TEST");
		final ArraValue intval = ArraValue.of(123);
		final ArraValue fptval = ArraValue.of(123.3);
		final ArraValue sysnul = Special.SYS_NULL;
		final ArraValue nulval = null;
		
		//Add values to stack 
		STACK.add(strval);
		STACK.add(intval);
		STACK.add(fptval);
		STACK.add(sysnul);
		STACK.add(nulval);
		
		//Transform to list
		final List<ArraValue> list = STACK.getVariableList();
		
		//Test
		assertEquals(list.size(),3);
		
		assertEquals(list.get(0),strval);
		assertEquals(list.get(1),intval);
		assertEquals(list.get(2),fptval);
	}
}
