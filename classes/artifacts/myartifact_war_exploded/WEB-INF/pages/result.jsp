<%--
  Created by IntelliJ IDEA.
  User: elena
  Date: 10/22/15
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul class="nav nav-sidebar">
        <p></p>
        <li>
          <form class="form-inline" action='${rootURL}index' method='GET' style="text-indent:15px">
            <button type="submit" class="btn btn-success">
              <span class="glyphicon glyphicon-pencil" aria-hidden="true"> </span>
              New game
            </button>
          </form>
        </li>
        </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h2 class="sub-header">The winner is ${winner}</h2>
      </div>
  </div>
</div>
</body>
</html>