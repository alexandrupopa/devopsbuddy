package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class UserIntegrationTest extends AbstractIntegrationTest {
	
	
	@Rule public TestName testName = new TestName();

	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	
	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}
	
	@Test
	public void testCreateNewRole() throws Exception {
		Role basicRole = createRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}
	
	@Test
	public void createNewUser() throws Exception{
		
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@devopsbuddy.com";
		
		User basicUser = createUser(username, email);
		
		User newlyCreatedUser = userRepository.findOne(basicUser.getId());
		Assert.assertNotNull(newlyCreatedUser);
		Assert.assertTrue(newlyCreatedUser.getId() != 0);
		Assert.assertNotNull(newlyCreatedUser.getPlan());
		Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		
		for (UserRole ur : newlyCreatedUserRoles) {
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		
		String username = testName.getMethodName();
		String email = testName.getMethodName()+"@devopsbuddy.com";
		
		User basicUser = createUser(username, email);
		userRepository.delete(basicUser.getId());
	}
	
	protected User createUser(TestName testName) {
		return createUser(testName.getMethodName(), testName.getMethodName()+"@devopsbuddy.com");
	}
	
}
