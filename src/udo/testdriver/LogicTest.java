package udo.testdriver;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import udo.logic.Logic;
import udo.storage.Storage;
import udo.storage.Task;
import udo.storage.Task.TaskType;

public class LogicTest {
    private void removeExistingTasks() {
        File settingFile = new File("setting.txt");
        if (settingFile.isFile()) {
            settingFile.delete();
        }

        File tasksFile = new File("setting.txt");
        if (tasksFile.isFile()) {
            tasksFile.delete();
        }
    }

    @Test
    public void testAdd1() {
        removeExistingTasks();
        
        Logic logic = Logic.getInstance();
        logic.setGUI(new GUIStub());
        
        Storage storage = new Storage();
        
        logic.executeCommand("go to school /deadline tomorrow");
        
        List<Task> tasks = storage.query();

        assertEquals(1, tasks.size());
        assertEquals(TaskType.DEADLINE, tasks.get(0).getTaskType());
        assertEquals("go to school", tasks.get(0).getContent());
    }

    @Test
    public void testAdd2() {
        removeExistingTasks();
        
        Logic logic = Logic.getInstance();
        logic.setGUI(new GUIStub());
        
        Storage storage = new Storage();
        
        assertEquals(true,
                     logic.executeCommand("add go to school /start tomorrow 2pm /end tomorrow 4pm"));
        
        List<Task> tasks = storage.query();

        assertEquals(1, tasks.size());
        assertEquals(TaskType.EVENT, tasks.get(0).getTaskType());
        assertEquals("go to school", tasks.get(0).getContent());
    }

    @Test
    public void testAdd3() {
        removeExistingTasks();
        
        Logic logic = Logic.getInstance();
        logic.setGUI(new GUIStub());
        
        Storage storage = new Storage();
        
        assertEquals(true,
                     logic.executeCommand("add watch a movie /duration 2 hours 30 minutes"));
        
        List<Task> tasks = storage.query();

        assertEquals(1, tasks.size());
        assertEquals(TaskType.TODO, tasks.get(0).getTaskType());
        assertEquals("watch a movie", tasks.get(0).getContent());
    }

    @Test
    public void testAdd4() {
        removeExistingTasks();
        
        Logic logic = Logic.getInstance();
        logic.setGUI(new GUIStub());
        
        Storage storage = new Storage();
        
        logic.executeCommand("go to school /deadline tomorrow");
        logic.executeCommand("add go to school /start tomorrow 2pm /end tomorrow 4pm");
        logic.executeCommand("add watch a movie /duration 2 hours 30 minutes");
        
        List<Task> tasks = storage.query();

        assertEquals(3, tasks.size());
    }
    
    @Test
    public void testInvalidDate() {
        removeExistingTasks();

        Logic logic = Logic.getInstance();
        logic.setGUI(new GUIStub());

        assertEquals(false,
                     logic.executeCommand("go to school /deadline tomrrow"));
    }
}