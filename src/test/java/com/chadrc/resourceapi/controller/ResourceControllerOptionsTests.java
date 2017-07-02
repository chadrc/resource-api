package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerOptionsTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void optionsSucceeds() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().options();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void optionsReturnsOptionsResult() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().options();
        assert responseEntity.getBody() instanceof OptionsResult;
    }

    @Test
    public void optionsHasUserEntry() {
        OptionsResult optionsResult = getOptions();
        assert optionsResult.getOptions().containsKey("User");
    }

    @Test
    public void userEntryHas2CreateOptions() {
        ResourceOptions userOptions = getUserResourceOptions();
        List<CreateOption> createOptions = userOptions.getCreateOptions();
        assertEquals(2, createOptions.size());
    }

    @Test
    public void hasZeroParameterCreateOption() {
        ResourceOptions userOptions = getUserResourceOptions();
        List<CreateOption> createOptions = userOptions.getCreateOptions();
        boolean success = false;
        for (CreateOption createOption : createOptions) {
            success = createOption.getParameters().size() == 0;
            if (success) {
                break;
            }
        }

        assertTrue(success);
    }

    @Test
    public void hasStringStringParameterCreateOption() {
        ResourceOptions userOptions = getUserResourceOptions();
        List<CreateOption> createOptions = userOptions.getCreateOptions();
        boolean success = false;
        for (CreateOption createOption : createOptions) {
            List<SchemaType> parameters = createOption.getParameters();
            success = createOption.getParameters().size() == 2
                    && parameters.get(0).getType() == String.class
                    && parameters.get(1).getType() == String.class;
            if (success) {
                break;
            }
        }
        assertTrue(success);
    }

    @Test
    public void userEntryHas2ActionOptions() {
        ResourceOptions userOptions = getUserResourceOptions();
        List<ActionOption> actionOptions = userOptions.getActionOptions();
        assertEquals(2, actionOptions.size());
    }

    @Test
    public void userEntryHasSignUpForNewsletterAction() {
        assertNotNull(getUserAction("signUpForNewsletter"));
    }

    @Test
    public void userEntryHasChangePasswordAction() {
        assertNotNull(getUserAction("changePassword"));
    }

    @Test
    public void signUpForNewsletterActionHasZeroParameters() {
        ActionOption actionOption = getUserAction("signUpForNewsletter");
        assertEquals(0, actionOption.getParameters().size());
    }

    @Test
    public void changePasswordActionHasTwoParameters() {
        ActionOption actionOption = getUserAction("changePassword");
        assertEquals(2, actionOption.getParameters().size());
    }

    @Test
    public void changePasswordActionParametersAreBothOfStringType() {
        ActionOption actionOption = getUserAction("changePassword");
        assertEquals(String.class, actionOption.getParameters().get(0).getType());
        assertEquals(String.class, actionOption.getParameters().get(1).getType());
    }

    private ActionOption getUserAction(String actionName) {
        ResourceOptions userOptions = getUserResourceOptions();
        List<ActionOption> actionOptions = userOptions.getActionOptions();
        ActionOption selected = null;
        for (ActionOption actionOption : actionOptions) {
            if (actionName.equals(actionOption.getName())) {
                selected = actionOption;
            }
        }
        return selected;
    }

    private OptionsResult getOptions() {
        return (OptionsResult) resourceControllerProxy.getResourceController().options().getBody();
    }

    private ResourceOptions getUserResourceOptions() {
        return getOptions().getOptions().get("User");
    }
}
