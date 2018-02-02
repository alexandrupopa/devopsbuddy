package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.devopsbuddy.DevopsbuddyApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest{

	@Rule public TestName testName = new TestName();

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceIntegrationTest.class);
	
	@Test
	public void testCreateNewUser() throws Exception {

		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}

	@Test
	public void testUpdatePassword() throws Exception {

		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		String oldPassword = user.getPassword();
		String newPassword = UUID.randomUUID().toString();

		userService.updateUserPassword(user.getId(), newPassword);
		user = userRepository.findOne(user.getId());
		Assert.assertNotEquals(oldPassword, user.getPassword());

	}
}
