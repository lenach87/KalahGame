<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
</head>
<body>

<div class="form-group">
    <form:form action="start" method="post" class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">

    <div class="form-group">
        <label for="name1" class="col-sm-2 control-label">First name</label>
        <div class="col-sm-4">
            <form:input path="name1" type="text" class="form-control" placeholder="First Player Name" autofocus="true"/>
            <form:errors path="name1" cssStyle="color: #ff0000;"/>
        </div>
    </div>
    <div class="form-group">
        <label for="name2" class="col-sm-2 control-label">Last name</label>
        <div class="col-sm-4">
            <form:input path="name2" type="text" class="form-control" placeholder="Second Player Name" />
            <form:errors path="name2" cssStyle="color: #ff0000;"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
            <button class="btn btn-lg btn-primary btn-block"  type="submit" value="/index"> Start game </button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>

