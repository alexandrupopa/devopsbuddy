package com.devopsbuddy.test.integration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.devopsbuddy.backend.service.PasswordResetTokenService;
import com.devopsbuddy.backend.persistence.domain.backend.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class PasswordresetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Rule public TestName testName = new TestName();

    @Test
    public void testCreateNewTokenForUserEmail () throws Exception {
        User user = createUser(testName);

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        Assert.assertNotNull(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getToken());

    }

    @Test
    public void testFindByToken() throws Exception {

        User user = createUser(testName);
        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        PasswordResetToken foundToken = passwordResetTokenService.findByToken(passwordResetToken.getToken());

        Assert.assertNotNull(foundToken);
        Assert.assertNotNull(foundToken.getToken());

    }
}
