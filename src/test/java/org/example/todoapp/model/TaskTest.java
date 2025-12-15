package org.example.todoapp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TaskTest {
    Task task = new Task("Description");

    @Test
    void getDescription() {
        String desc = task.getDescription();
        Assertions.assertEquals("Description", desc);
    }

    @Test
    void testStatus() {
        Assertions.assertFalse(task.isCompleted());

        task.setCompleted(true);
        Assertions.assertTrue(task.isCompleted());
    }
}
