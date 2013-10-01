scalatra-angularjs-portlet
==========================

Sample Liferay portlet that uses AngularJS to display a list of user
served via a REST API powered by Scalatra. The REST API is served via
Liferay's delegate servlet in order to access Liferay services with
permission checking enabled. A Scalatra security strategy is used to
setup the Liferay permission checker for all incoming requests.

To make it easy to extend the REST API, individual controllers are
implemented as traits, which are then mixed into the main servlet.
Dependencies are injected into the controllers using SubCut binding
modules.

This portlet is derived from the angularjs-sample-portlet made by Dmitri
Carpov. Apart from the rewrite to Scala it also includes the AngularJS
code as part of the portlet.
