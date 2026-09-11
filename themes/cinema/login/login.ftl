<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "header">
        <h2 style="color: #e50914;">Cinema Tickets</h2>
    <#elseif section = "form">
        <div class="login-box">
            <h3>Sign In to Your Account</h3>

            <#if realm.password>
                <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                    <input id="username" name="username" type="text" placeholder="Email or Username" autofocus autocomplete="off" />
                    <input id="password" name="password" type="password" placeholder="Password" autocomplete="off" />
                    <button class="btn-primary" name="login" id="kc-login" type="submit">Log In</button>
                </form>
            </#if>

            <#if realm.password && social.providers??>
                <hr style="border-color: #444; margin: 20px 0;">
                <p>Or sign in with</p>
                <#list social.providers as p>
                    <a href="${p.loginUrl}" class="btn-primary google-btn" style="display: block; text-decoration: none;">
                        Sign in with ${p.displayName!}
                    </a>
                </#list>
            </#if>
        </div>
    </#if>
</@layout.registrationLayout>
