<%@ page import="listofbusiness.Business" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'business.label', default: 'Business')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-business" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        <li><g:uploadForm  name="myUpload"
                           controller="business"
                           action="upload"
                           enctype="multipart/form- data">
            <input type="file" name="myFile"/>
            <g:submitButton name="upload" class="save" value="Upload" />
        </g:uploadForm></li>

        <li><g:form controller="business" action="search" method="POST" >
            <g:textField name="key" />
            <g:submitButton name="search" value="search" class = "save"/>
        </g:form></li>
    </ul>
</div>
<div id="list-business" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <table>
        <thead>
            <tr>
                <g:sortableColumn property="name" title="name"/>
                <g:sortableColumn property="email" title="email"/>
                <g:sortableColumn property="street" title="street"/>
                <g:sortableColumn property="zip" title="zip"/>
            </tr>
        </thead>
                <tbody>
            <g:each in = "${bList}" var ="b" status = "i" >
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>"${b.name}"</td>
                <td>"${b.email}"</td>
                <td>"${b.street}"</td>
                <td>"${b.zip}"</td>
                </tr>
            </g:each>
                </tbody>
    </table>

</div>
</body>
