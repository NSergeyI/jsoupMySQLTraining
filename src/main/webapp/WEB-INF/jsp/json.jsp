<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Yarnovosti</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<a href="/">main</a>
<a href="/parse">parse</a>
<a href="/json">json</a>
<div>
    ${json}
</div>
<script src="/js/main.js"></script>
</body>
</html>