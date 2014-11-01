package com.app.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.auth.AuthManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class LoginFormListener implements Button.ClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    private AuthManager authManager;

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();
            LoginView parent = (LoginView) source.getParent();
            String username = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();

            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);

            Authentication result = authManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);

            Navigator navigator = UI.getCurrent().getNavigator();
            navigator.navigateTo("user");

        } catch (AuthenticationException e) {
            Notification.show("Authentication failed: " + e.getMessage());
        }

    }

}
