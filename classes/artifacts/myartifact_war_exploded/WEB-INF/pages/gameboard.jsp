<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
</head>
<body>
<c:if test="${players != null}">
  <c:forEach items="${players}" var="player">
    <c:choose>
      <c:when test="${player.inTurn==true}">
        <h2 class="sub-header">Acting player - ${player.getName()}  </h2>
      </c:when>
      <c:otherwise>
        <h2 class="sub-header">Opposite player - ${player.getName()}  </h2>
      </c:otherwise>
    </c:choose>
    </c:forEach>
  </c:if>


  <input type="number" name="numberOfPit" />


        <div class="table-responsive">
          <form class="form-inline">
          <table class="table table-striped">
            <colgroup>
              <col span="1" style="width: 20%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 10%;">
              <col span="1" style="width: 20%;">

            </colgroup>
            <thead>
            <tr>
            <input type="number" name="numberOfPit" action="${rootURL}gameboard" method="post" />
              <button type="submit" class="btn btn-primary">
                             Make move
              </button>
            </tr>
            <tr>
              <td>
                Kalah
              </td>
              <td><b>6</b></td>
              <td><b>5</b></td>
              <td><b>4</b></td>
              <td><b>3</b></td>
              <td><b>2</b></td>
              <td><b>1</b></td>


            </tr>
            </thead>
            <c:if test="${players != null}">
            <c:forEach items="${players}" begin="0" end="0" var="player1">

              <tr>
              <td> ${player1.getKalahForPlayer().getStonesInKalah()}
              </td>
              <td> ${player1.getPitsForPlayer()[5].getStonesInPit()}
                </td>
                <td> ${player1.getPitsForPlayer()[4].getStonesInPit()}
                </td>
                <td> ${player1.getPitsForPlayer()[3].getStonesInPit()}
                </td>
                <td> ${player1.getPitsForPlayer()[2].getStonesInPit()}
                </td>
                <td> ${player1.getPitsForPlayer()[1].getStonesInPit()}
                </td>
                <td> ${player1.getPitsForPlayer()[0].getStonesInPit()}
                </td>
              <td>
              </td>
              </tr>
              </c:forEach>
              </c:if>

                <c:if test="${players != null}">
                <c:forEach items="${players}" begin="1" end="1" var="player2">


                  <tr>
                    <td>
                  </td>
                    <td> ${player2.getPitsForPlayer()[0].getStonesInPit()}
                    </td>
                    <td> ${player2.getPitsForPlayer()[1].getStonesInPit()}
                    </td>
                    <td> ${player2.getPitsForPlayer()[2].getStonesInPit()}
                    </td>
                    <td> ${player2.getPitsForPlayer()[3].getStonesInPit()}
                    </td>
                    <td> ${player2.getPitsForPlayer()[4].getStonesInPit()}
                    </td>
                    <td> ${player2.getPitsForPlayer()[5].getStonesInPit()}
                    </td>
                    <td>  ${player2.getKalahForPlayer().getStonesInKalah()}
                    </td>
                  </tr>

                  <tr>
                    <td>
                    </td>
                    <td> 1
                    </td>
                    <td> 2
                    </td>
                    <td> 3
                    </td>
                    <td> 4
                    </td>
                    <td> 5
                    </td>
                    <td> 6
                    </td>
                    <td>  Kalah
                    </td>
                  </tr>
                  </c:forEach>
                  </c:if>

              </tbody>
            </table>
          </form>
        </div>

</body>
</html>
