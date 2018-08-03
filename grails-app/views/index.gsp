<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>

</head>
<body>
    <content tag="nav">

    </content>

    <div id="content" role="main">
        <section class="row colset-2-its">

            <div id="controllers" role="navigation">
                <h2>Menu</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName} }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.name}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>
        </section>
    </div>

</body>
</html>
