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
<table>
    <tr>
        <th>ID</th>
        <th>link</th>
        <th>date</th>
        <th>title</th>
        <th>text</th>
        <th>tag</th>
    </tr>
    <c:forEach items="${news}" var="article">
        <tr>
            <td>${article.id}</td>
            <td>${article.link}</td>
            <td>${article.date}</td>
            <td>${article.title}</td>
            <td>${article.text}</td>
            <td>
                <table>
                    <c:forEach items="${article.tags}" var="tag">
                        <tr>
                            <td>${tag.name}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>
<script src="/js/main.js"></script>
</body>
</html>