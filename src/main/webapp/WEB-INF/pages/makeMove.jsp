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
  <c:url var="pit" value="${rootURL}makeMove" />
  <form:form method="post" modelAttribute="makeMove" action="${pit}"
             class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
    <form:hidden path="id"/>
    <input type="number" name="tempNumber"/>
    <button type="submit" class="btn btn-primary">
      Make move
    </button>
    <c:choose>
      <c:when test="${makeMove.asFirst==true}">
        <h2 class="sub-header">Acting player - ${makeMove.player1}  </h2>
      </c:when>
      <c:otherwise>
        <h2 class="sub-header">Acting player - ${makeMove.player2}  </h2>
      </c:otherwise>
    </c:choose>
  </form:form>
</div>

<div class="table-responsive">
  <form class="form-inline">
    <table class="table table-striped">
      <colgroup>
        <col span="1" style="width: 35%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 5%;">
        <col span="1" style="width: 35%;">

      </colgroup>

      <tr>
        <td>
          <b> Kalah for ${makeMove.player2} </b>
        </td>
        <td><b>#6</b></td>
        <td><b>#5</b></td>
        <td><b>#4</b></td>
        <td><b>#3</b></td>
        <td><b>#2</b></td>
        <td><b>#1</b></td>
      </tr>
      <tr>
        <td> [ ${makeMove.initialPlayer2.getKalahForPlayer().getStonesInKalah()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(5).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(4).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(3).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(2).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(1).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer2.getPitsForPlayer().get(0).getStonesInPit()} ]
        </td>
        <td>
        </td>
      </tr>


      <tr>
        <td>
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(0).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(1).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(2).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(3).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(4).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getPitsForPlayer().get(5).getStonesInPit()} ]
        </td>
        <td> [ ${makeMove.initialPlayer1.getKalahForPlayer().getStonesInKalah()} ]
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
        <td> <b> Kalah for ${makeMove.initialPlayer1.getName()} </b>
        </td>
      </tr>

      </tbody>
    </table>
  </form>
</div>

</body>
</html>
