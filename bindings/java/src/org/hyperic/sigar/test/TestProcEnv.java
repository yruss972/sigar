package org.hyperic.sigar.test;

import java.io.File;
import java.util.Map;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.SigarPermissionDeniedException;

public class TestProcEnv extends SigarTestCase {

    public TestProcEnv(String name) {
        super(name);
    }

    public void testCreate() throws Exception {
        Sigar sigar = getSigar();

        try {
            sigar.getProcEnv(getInvalidPid());
        } catch (SigarException e) {
        }

        long pid = sigar.getPid();

        try {
            Map env = sigar.getProcEnv(pid);
            traceln(env.toString());

            String key = "JAVA_HOME";
            String val = (String)env.get(key);

            String single = sigar.getProcEnv(pid, key);

            if (val != null) {
                assertTrue(new File(val, "bin").exists());
                assertTrue(val.equals(single));
                traceln(key + "==>" + single);
            }

            key = "dOeSnOtExIsT";
            val = (String)env.get(key);
            assertTrue(val == null);

            val = sigar.getProcEnv(pid, key);
            assertTrue(val == null);
        } catch (SigarNotImplementedException e) {
            //ok
        } catch (SigarPermissionDeniedException e) {
            //ok
        }
    }
}