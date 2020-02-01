<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:pbl="https://www.xscience.com/data/publication.xsd"
    exclude-result-prefixes="xs"
    version="2.0">
    
    <xsl:template match="/">
        <html>
            <head>
                <title>
                    <xsl:value-of select="pbl:title"/>
                </title>
            </head>
        </html>
    </xsl:template>
</xsl:stylesheet>