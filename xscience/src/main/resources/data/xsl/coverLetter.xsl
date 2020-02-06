<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:pbl="https://www.xscience.com/data/publication.xsd"
    xmlns:cl="https://www.xscience.com/data/coverLetter.xsd"
    exclude-result-prefixes="xs"
    version="2.0">
    
    <xsl:template match="/">
        
        <html>
            
            <head>
                
                <title>
                    
                </title>
                <style type="text/css">
                    
                    h3 {
                    text-align: center;
                    }
                    h1 {
                    text-align: center;
                    }
                    body { font-family: sans-serif; }
                    
                    .sup {
                    vertical-align: super;
                    padding-left: 4px;
                    font-size: small;
                    text-transform: lowercase;
                    }
                    .quote {
                    vertical-align: super;
                    padding-left: 4px;
                    font-size: small;
                    text-transform: lowercase;
                    }
                    
                </style>
            </head>
            <body>
                <h1>
                    <xsl:value-of select="/cl:CoverLetter/cl:PublicationTitle"/>
                </h1>
                
                <xsl:for-each select="cl:CoverLetter/cl:Author">
                    
                    <p>
                        
                        [<xsl:value-of select="self::node()/pbl:Name/pbl:FirstName"/> 
                        &#160; 
                        <xsl:value-of select="self::node()/pbl:Name/pbl:MiddleName"/>
                        &#160;
                        <xsl:value-of select="self::node()/pbl:Name/pbl:LastName"/>
                    
                        <br/>
                    
                        <xsl:value-of select="self::node()/pbl:Affiliation/pbl:University"/>
                        <br/>
                        <xsl:value-of select="self::node()/pbl:Affiliation/pbl:City"/>
                        ,
                        <xsl:value-of select="self::node()/pbl:Affiliation/pbl:Country"/> 
                        <br/>
                        <xsl:value-of select="self::node()/pbl:Email"/>] 
                    </p>
                    
                </xsl:for-each>
                <br/>
                <p>
                    [<xsl:value-of select="self::node()/cl:CoverLetter/cl:Editor"/>
                    <br/>
                    <xsl:value-of select="self::node()/cl:CoverLetter/cl:Journal"/>]
                </p>
                <br/>
                [<xsl:value-of select="/cl:CoverLetter/@submissionDate"/>]
                <br/>
                <xsl:apply-templates select="/cl:CoverLetter/cl:Content"></xsl:apply-templates>
            </body>
        </html>
    </xsl:template>
    
    
    
    <xsl:template match="//cl:List">
        <xsl:variable name="ordered" select="self::node()/@ordered"/>
        <xsl:choose>
            <xsl:when test="@ordered=false()">
                <xsl:variable name="unordered_style" select="self::node()/@unordered_style"/>
                <xsl:choose>
                    <xsl:when test="@unordered_style='CIRCLE'">
                        <ul style="list-style-type:circle;">
                            <xsl:for-each select="self::node()/pbl:ListItem">
                                <li>
                                    <xsl:apply-templates select="self::node()"></xsl:apply-templates>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:when>
                    <xsl:otherwise>
                        <ul>
                            <xsl:for-each select="self::node()/pbl:ListItem">
                                <li>
                                    <xsl:apply-templates select="self::node()"></xsl:apply-templates>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:otherwise>
                </xsl:choose>      
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:variable name="ordered_style" select="self::node()/@ordered_style"/>
                <ol type="{$ordered_style}">
                    <xsl:for-each select="self::node()/pbl:ListItem">
                        <li>
                            <xsl:apply-templates select="self::node()"></xsl:apply-templates>
                        </li>
                    </xsl:for-each>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
        <br/>  
    </xsl:template>
    
    <xsl:template match="//pbl:ListItem" mode="handle-emphasis">
        <xsl:value-of select="text()"/>
    </xsl:template>
    
    <xsl:template match="//pbl:Bold">
        <b>
            <xsl:value-of select="current()"/>
        </b>
    </xsl:template>
    <xsl:template match="//pbl:Underline">
        <u>
            <xsl:value-of select="current()"/>
        </u>
    </xsl:template>
    
    <xsl:template match="//pbl:Italic">
        <i>
            <xsl:value-of select="current()"/>
        </i>
    </xsl:template>
    
    <xsl:template match="//cl:CLSection" mode="handle-emphasis">
        <xsl:value-of select="current()"/>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Italic"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Underline"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Bold"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/cl:List"></xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="//cl:Content">
        <xsl:apply-templates select="self::node()/cl:CLSection"></xsl:apply-templates>
    </xsl:template>
</xsl:stylesheet>








