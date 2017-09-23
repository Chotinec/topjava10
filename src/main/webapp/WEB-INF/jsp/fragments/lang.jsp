<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a onclick="show('en')">English</a> </li>
        <li><a onclick="show('ru')">Russian</a> </li>
    </ul>
</li>

<script type="text/javascript">
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?language=' + lang;
    }
</script>
