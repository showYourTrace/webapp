<%--<%@ page import="java.util.Date" %>--%>
<!doctype html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html xmlns:ng="http://angularjs.org" id="ng-app" ng-app="showyourtraceAdmin">
<head>
  <%--<base href="/">--%>
  <title>showyourtrace admin console</title>
  <meta http-equiv="X-UA-Compatible" content="IE=8" />
  <meta name="viewport" content="width=device-width, initial-scale=0.8">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta http-equiv="cache-control" content="no-cache; no-store; must-revalidate">
  <meta http-equiv="Content-Type" content="html; charset=utf-8;"/>

  <% double version = 2d;%>

  <%--<script src='<c:url value="/resources/new/es5-shim.js"/>?v=<%=version%>'></script>--%>

  <!--[if lte IE 9]>
  <script src='<c:url value="/resources/lib/html5shiv.min.js"/>?v=<%=version%>'></script>
  <script src='<c:url value="/resources/lib/html5shiv-printshiv.min.js"/>?v=<%=version%>'></script>
  <![endif]-->
  <!--[if lte IE 8]>
  <script src='<c:url value="/resources/lib/json3.js"/>?v=<%=version%>'></script>
  <![endif]-->

  <link href='<c:url value="/resources/css/bootstrap.css"/>?v=<%=version%>' rel="stylesheet">
  <link href='<c:url value="/resources/css/application.css"/>?v=<%=version%>' rel="stylesheet">
  <link href='<c:url value="/resources/fonts/font-awesome/css/font-awesome.css"/>?v=<%=version%>' rel="stylesheet">

  <!--[if lte IE 9]>
  <script src='<c:url value="/resources/lib/respond.min.js"/>?v=<%=version%>'></script>
  <![endif]-->

</head>
<body>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-12">
      <ui-view></ui-view>
    </div>
  </div>
</div>

<div id="mydiv" loading>
  <img src="/showyourtrace/resources/img/loading.gif" class="ajax-loader"/>
</div>

<script src="<c:url value="/resources/lib/jquery-1.11.3.js"/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/angular.js"/>?v=<%=version%>"></script>
<script src="<c:url value='/resources/lib/underscore.js'/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/restangular.js"/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/ui-bootstrap-tpls.js"/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/angular-resource.js"/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/angular-route.js"/>?v=<%=version%>"></script>
<script src="<c:url value='/resources/lib/angular-ui-router.js'/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/angular-local-storage.js"/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/lib/angular-file-upload.min.js"/>?v=<%=version%>"></script>

<script src="<c:url value="/resources/js/taginput.js"/>?v=<%=version%>"></script>
<% double key = 101d;//Math.random() * 100;%>
<script src="<c:url value="/resources/js/loading.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/dateElement.js"/>?v=<%=key%>"></script>

<script src="<c:url value='/resources/js/services.js'/>?v=<%=version%>"></script>

<script src="<c:url value="/resources/js/admin/editPageCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/searchPageCtrl.js"/>?v=<%=key%>"></script>

<%--<script src="<c:url value="/resources/js/admin/orders/ordersAdminCtrl.js"/>?v=<%=key%>"></script>--%>
<%--<script src="<c:url value="/resources/js/admin/orders/ordersAdminNewDealCtrl.js"/>?v=<%=key%>"></script>--%>
<%--<script src="<c:url value="/resources/js/admin/orders/ordersAdminModule.js"/>?v=<%=key%>"></script>--%>

<script src="<c:url value="/resources/js/admin/deal/dealAdminServices.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/deal/dealAdminEditCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/deal/dealAdminEditOfferCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/deal/dealAdminSearchCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/deal/dealOfferAdminSearchCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/deal/dealAdminModule.js"/>?v=<%=key%>"></script>

<script src="<c:url value="/resources/js/admin/booking/bookingAdminServices.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/booking/bookingAdminEditCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/booking/bookingAdminSearchCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/booking/bookingAdminModule.js"/>?v=<%=key%>"></script>

<script src="<c:url value="/resources/js/admin/vendor/vendorAdminServices.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/vendor/vendorAdminEditCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/vendor/vendorAdminSearchCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/vendor/vendorAdminModule.js"/>?v=<%=key%>"></script>

<script src="<c:url value="/resources/js/admin/user/userAdminServices.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/user/userAdminEditCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/user/userAdminSearchCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/user/userAdminModule.js"/>?v=<%=key%>"></script>

<script src="<c:url value="/resources/js/admin/mailing/mailingAdminServices.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/mailing/mailingAdminCtrl.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/mailing/mailingAdminModule.js"/>?v=<%=key%>"></script>

<script src="<c:url value="/resources/js/configuration.js"/>?v=<%=key%>"></script>
<script src="<c:url value="/resources/js/admin/admin.js"/>?v=<%=key%>"></script>
<script src="<c:url value='/resources/js/authenticate.js'/>?v=<%=version%>"></script>
<script src="<c:url value="/resources/js/directives.js"/>?v=<%=key%>"></script>

</body>
</html>
