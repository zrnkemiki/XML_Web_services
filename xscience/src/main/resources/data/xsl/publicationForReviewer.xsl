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
                    <xsl:value-of select="pbl:Publication/pbl:Title"/>
                </title>
                <style type="text/css">
                    
                    h3 {
                    text-align: center;
                    }
                    h1 {
                    text-align: center;
                    }
                    body { font-family: sans-serif; }
                    p { text-indent: 30px; }
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
                    <xsl:value-of select="pbl:Publication/pbl:Title"/>
                </h1>
                    <h3>   
                        XXXXX XXXXX
                    </h3>
                <h5>Abstract</h5>
                
                <xsl:apply-templates select="/pbl:Publication/pbl:Abstract"></xsl:apply-templates>
                <xsl:apply-templates select="/pbl:Publication/pbl:Content"></xsl:apply-templates>
                <xsl:apply-templates select="/pbl:Publication/pbl:References"></xsl:apply-templates>
                
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Abstract" mode="handle-emphasis">
        <xsl:apply-templates select="self::node()/pbl:Quote"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Content"></xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Abstract/pbl:Content" mode="handle-emphasis">
        
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Italic"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Underline"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Bold"></xsl:apply-templates>
        
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
    
    <xsl:template match="//pbl:Quote">
        <br/>
        <p class = "quote">
            <xsl:value-of select="self::node()/pbl:QuoteContent"/>
            (
            
            <xsl:value-of select="self::node()/pbl:Source"/>
            )
        </p>    
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Content">
        <xsl:apply-templates select="self::node()/pbl:Paragraph"></xsl:apply-templates>
        
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Content/pbl:Paragraph">
        <h2>
            <xsl:value-of select="self::node()/pbl:ParagraphTitle"/>
        </h2>
        <xsl:apply-templates select="self::node()/pbl:ParagraphContent"></xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Content/pbl:Paragraph/pbl:ParagraphContent" mode="handle-emphasis">
        <xsl:apply-templates select="self::node()/pbl:Quote"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Italic"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Underline"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Bold"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Figure"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:List"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Table"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Hyperlink"></xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Content/pbl:Paragraph/pbl:ParagraphContent/pbl:Figure">
        <img src="https://landing.moqups.com/img/content/charts-graphs/pie-donut-charts/simple-pie-chart/simple-pie-chart-1600.png" alt="Smiley face" height="300" width="300" style="display: block;"/>
        <br/>
        <xsl:value-of select="self::node()/pbl:Description"/>
        <br/>
    </xsl:template>
    
    <xsl:template match="/pbl:Publication/pbl:Content/pbl:Paragraph/pbl:ParagraphContent/pbl:List">
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
    
    <xsl:template match="/pbl:Publication/pbl:Content/pbl:Paragraph/pbl:ParagraphContent/pbl:Table">
        <table border = "solid">
            <xsl:for-each select="self::node()/pbl:Row">
                <xsl:apply-templates select="self::node()"></xsl:apply-templates>
            </xsl:for-each>
        </table>
        <xsl:value-of select="self::node()/pbl:Description"/>
        <br/>
    </xsl:template>
    <xsl:template match="//pbl:Row">
        <tr>
            <xsl:for-each select="self::node()/pbl:Cell">
                <td>
                    <xsl:value-of select="self::node()/text()"/>
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>
    
    <xsl:template match="//pbl:Hyperlink">
        <xsl:variable name="href" select="self::node()/@href"/>
        <a href="{$href}">
            <xsl:value-of select="current()"/>
        </a>  
    </xsl:template>
    
    <xsl:template match="//pbl:References">
        <b>References</b>
        <br/>
        <xsl:for-each select="self::node()/pbl:Reference">
            <xsl:apply-templates select="self::node()"></xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="//pbl:Reference">
        <xsl:variable name="href" select="self::node()/@href"/>
        <p>
            <xsl:value-of select="self::node()/pbl:Author"/> 
            (
            <xsl:value-of select="self::node()/pbl:Year"/>
            ).
            <xsl:value-of select="self::node()/pbl:Title"/>
            <a href = "{$href}">
                <xsl:value-of select="self::node()/pbl:JournalName"/>
            </a>,
            <xsl:value-of select="self::node()/pbl:VolumeIssue"/>,
            pp.
            <xsl:value-of select="self::node()/pbl:Pages"/>
        </p>
    </xsl:template>
</xsl:stylesheet>









