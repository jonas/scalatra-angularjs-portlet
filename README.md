scalatra-angularjs-portlet
==========================

Sample Liferay portlet that uses AngularJS on the front-end to display a
list of user and Scalatra on the back-end to provide a REST API. The
REST API is served via Liferay's delegate servlet in order to have
access to Liferay's session information and especially the logged in
user.

It uses Scalatra 2.0 because Liferay's delegate servlet does not support
asynchronous servlets, which are required by Scalatra 2.1+.

This portlet is derived from the angularjs-sample-portlet made by Dmitri
Carpov. Apart from the rewrite to Scala it also includes the AngularJS
code as part of the portlet.
