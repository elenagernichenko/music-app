<%@ page import="com.itis.model.Playlist" %>
<%@ page import="java.util.List" %>
<%@ page import="com.itis.dto.Info" %>
<%@ page import="com.itis.model.User" %>
<%@ page import="com.itis.dto.Roles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1>Admin Dashboard</h1>
<h2>Role Users</h2>
<form id="adminForm" method="post" action="/administration">
    <label for="user">Select User:</label>
    <select name="userId" id="user">
        <%
            List<User> userList = (List<User>) request.getAttribute("users");
            for (User user : userList) {
        %>
        <option value="<%= user.getId() %>"><%= user.getUsername() %></option>
        <%
            }
        %>
    </select>

    <label for="role">Select Role:</label>
    <select name="roleId" id="role">
        <%
            List<Roles> rolesList = (List<Roles>) request.getAttribute("roles");
            for (Roles role : rolesList) {
        %>
        <option value="<%= role.getId() %>"><%= role.getName() %></option>
        <%
            }
        %>
    </select>

    <input type="submit" value="Save Role">
</form>

<h2>Playlists</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Action</th>
    </tr>
    <%
        List<Playlist> playlists = (List<Playlist>) request.getAttribute("playList");
        for (Playlist playlist : playlists) {
    %>
    <tr>
        <td><%= playlist.getId() %></td>
        <td><%= playlist.getName() %></td>
        <td>
            <form action="/administration" method="post">
                <input type="hidden" name="playlistId" value="<%= playlist.getId() %>" />
                <input type="submit" value="Delete" />
            </form>
        </td>
    </tr>
    <% } %>
</table>

<h2>Tracks</h2>
<table>
    <tr>
        <th>Track Name</th>
        <th>Create Date</th>
        <th>Playlist Name</th>
        <th>User Name</th>
        <th>Action</th>
    </tr>
        <%
            List<Info> infoList = (List<Info>) request.getAttribute("infoList");
            for (Info info : infoList) {
        %>
    <tr>
        <td><%= info.getTrackName() %></td>
        <td><%= info.getCreateDate() %></td>
        <td><%= info.getPlayListName() %></td>
        <td><%= info.getUserName() %></td>
        <td>
            <form action="/administration" method="post">
                <input type="hidden" name="trackId" value="<%= info.getTrackId() %>" />
                <input type="submit" value="Delete" />
            </form>
        </td>
    </tr>
    <% } %>
</table>
<%@ include file="footer.jsp" %>
