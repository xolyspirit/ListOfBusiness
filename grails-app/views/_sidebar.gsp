<div id=”sidebar”>
    <g:if test="${session.getAttribute('user')}">
        <div id = "logout">
            <g:form controller="user" action="logout">
                <li><g:submitButton  name="logout" value="Logout"/></li>
            </g:form>
        </div>
    </g:if>
</div>
