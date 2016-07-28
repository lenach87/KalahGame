<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
</head>
<body>
<div class="form-group">

  <form:form method="post" modelAttribute="makeMove" action="${rootURL}makeMove"
             class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
    <form:hidden path="id"/>
    <input type="number" name="numberOfPitForLastMove"/>
    <button type="submit" class="btn btn-primary">
      Make move
    </button>
    <c:choose>
      <c:when test="${makeMove.asFirst==true}">
        <h2 class="sub-header">Acting player - ${makeMove.initialFirstPlayer.name}  </h2>
      </c:when>
      <c:otherwise>
        <h2 class="sub-header">Acting player - ${makeMove.initialSecondPlayer.name}  </h2>
      </c:otherwise>
    </c:choose>
  </form:form>
</div>

<div class="table-responsive">
  <form class="form-inline">
    <table class="table table-striped">
      <colgroup>
        <col span="1" style="width: 22%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 8%;">
        <col span="1" style="width: 22%;">
      </colgroup>

      <tr>
        <td>
          <b> Kalah for ${makeMove.initialSecondPlayer.name} </b>
        </td>
        <td><b>#6</b></td>
        <td><b>#5</b></td>
        <td><b>#4</b></td>
        <td><b>#3</b></td>
        <td><b>#2</b></td>
        <td><b>#1</b></td>
        <td><b></b></td>
      </tr>
      <tr>
        <td> [ ${makeMove.initialSecondPlayer.getKalahForPlayer()} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[5]} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[4]} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[3]} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[2]} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[1]} ]
        </td>
        <td> [ ${makeMove.initialSecondPlayer.getPitsForPlayer()[0]} ]
        </td>
        <td><b></b></td>
        <td>
        </td>
      </tr>


      <tr>
        <td>
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[0]} ]
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[1]} ]
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[2]} ]
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[3]} ]
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[4]} ]
        </td>
        <td> [ ${makeMove.initialFirstPlayer.getPitsForPlayer()[5]} ]
        </td>
        <td><b></b></td>
        <td> [ ${makeMove.initialFirstPlayer.getKalahForPlayer()} ]
        </td>

      </tr>

      <tr>
        <td>
        </td>
        <td><b>#1</b></td>
        <td><b>#2</b></td>
        <td><b>#3</b></td>
        <td><b>#4</b></td>
        <td><b>#5</b></td>
        <td><b>#6</b></td>
        <td><b></b></td>
        <td> <b> Kalah for ${makeMove.initialFirstPlayer.getName()} </b>
        </td>
      </tr>

      </tbody>
    </table>
  </form>
</div>

</body>
</html>
