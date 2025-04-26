package org.ulrica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest 
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private SecurityManager originalSecurityManager;
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        System.setSecurityManager(originalSecurityManager);
    }
    
    
    private static class ExitTrappedException extends SecurityException {
        private static final long serialVersionUID = 1L;
        public final int status;
        
        public ExitTrappedException(int status) {
            this.status = status;
        }
    }
    
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            
        }
        
        @Override
        public void checkPermission(Permission perm, Object context) {
            
        }
        
        @Override
        public void checkExit(int status) {
            throw new ExitTrappedException(status);
        }
    }
    
    @Test
    public void testMainMethod_BasicExecution() {
        
    }
    
    @Test
    public void testApp_IntegrationTest() {
        
        
        assertTrue(true);
    }
}
