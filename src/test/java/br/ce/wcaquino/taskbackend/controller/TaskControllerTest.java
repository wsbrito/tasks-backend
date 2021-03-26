package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;

	private Task todo;
	
	@Before 
	public void criarObjetos() {
		MockitoAnnotations.initMocks(this);
		todo = new Task();
		//controller = new TaskController();
	}
	
	@After
	public void limparObjetos() {
		todo = null;
		controller = null;
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("This line don't be executed!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		todo.setTask("Descricao");
		try {
			controller.save(todo);
			Assert.fail("This line don't be executed!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}		
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		todo.setDueDate(LocalDate.now().minusYears(10));
		todo.setTask("Descricao");
		try {
			controller.save(todo);
			Assert.fail("This line don't be executed!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		todo.setDueDate(LocalDate.now());
		todo.setTask("Descricao");
		controller.save(todo);
		Mockito.verify(taskRepo).save(todo);
	}

}
