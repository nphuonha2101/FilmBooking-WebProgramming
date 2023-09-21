<%--
  Created by IntelliJ IDEA.
  User: NhaNguyen
  Date: 15-09-2023
  Time: 6:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="content section centered-content">
    <div class="centered-content container form__container">
        <h2 class="title">Signup your account</h2>
        <span class="successful-span">${successfulMessage}</span>
        <form action="signup" method="post">
            <label for="username">Username</label> <span class="error-span" id="username-error">${usernameError}</span>
            <input type="text" name="username" id="username" placeholder="Username" required>
            <label for="user-full-name">Full name</label> <span class="error-span" id="fullname-error">${fullnameError}</span>
            <input type="text" name="user-full-name" id="user-full-name" placeholder="Full name" required>
            <label for="email">Email</label> <span class="error-span" id="email-error">${emailError}</span>
            <input type="email" name="email" id="email" placeholder="Email" required>
            <label for="password">Password</label> <span class="error-span" id="password-error">${passwordError}</span>
            <input type="password" name="password" id="password" placeholder="Password" required>
            <label for="confirm-password">Confirm password</label> <span
                class="error-span" id="confirm-password-error">${confirmPasswordError}</span>
            <input type="password" name="confirm-password" id="confirm-password" placeholder="Confirm Password"
                   required>

            <input type="submit" class="primary-filled-button button" value="Register">
        </form>
        <p>If you already have an account. <span><a href="login">Login now!</a> </span></p>

    </div>
</section>

