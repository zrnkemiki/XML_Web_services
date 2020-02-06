<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:pbl="https://www.xscience.com/data/publication.xsd"
    xmlns:rev="https://www.xscience.com/data/review.xsd"
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
                    .border {
                    border-style: solid;
                    border-width: 1px;
                    padding: 35px;
                    }
                    
                </style>
            </head>
            <body>
                <h1>
                    REVIEW
                </h1>
                <p>
                    Article Title:
                    <xsl:value-of select="/rev:Review/rev:PublicationTitle"/>
                    <br/>
                    Reviewer's Name:
                    <xsl:value-of select="/rev:Review/rev:Content/rev:Reviewer/pbl:Name/pbl:FirstName"/> 
                    &#160; 
                    <xsl:value-of select="/rev:Review/rev:Content/rev:Reviewer/pbl:Name/pbl:LastName"/>
                    <br/>
                    Recomendetion To Editor:
                    <b>
                    <xsl:value-of select="/rev:Review/rev:Content/rev:RecommendationForEditor"/>
                    </b>
                    <br/>
                    Comment For Editor:
                    <div class = "border">
                        <xsl:apply-templates select="/rev:Review/rev:Content/rev:CommentForEditor"></xsl:apply-templates>
                    </div>
                    <br/>
                </p>
                EVALUATION:
                <table border = "solid">
                    <tr>
                        <th>Item</th>
                        <th>Grade</th>
                    </tr>
                    <tr>
                        <td>Originality</td>
                        <td><xsl:value-of select ="/rev:Review/rev:Content/rev:Evaluation/rev:Originality"/></td>
                    </tr>
                    <tr>
                        <td>Adequate Literature</td>
                        <td><xsl:value-of select ="/rev:Review/rev:Content/rev:Evaluation/rev:AdequateLiterature"/></td>
                        
                    </tr>
                    <tr>
                        <td>Methodology</td>
                        <td><xsl:value-of select ="/rev:Review/rev:Content/rev:Evaluation/rev:Methodology"/></td>
                        
                    </tr>
                    <tr>
                        <td>Inference</td>
                        <td><xsl:value-of select ="/rev:Review/rev:Content/rev:Evaluation/rev:Inference"/></td>
                        
                    </tr>
                    <tr>
                        <td>Readability</td>
                        <td><xsl:value-of select ="/rev:Review/rev:Content/rev:Evaluation/rev:Readability"/></td>
                        
                    </tr>
                </table>
   
                <br/>
                
                Comment For Author:
                <div class = "border" >
                    <xsl:apply-templates select="/rev:Review/rev:Content/rev:CommentForAuthor"></xsl:apply-templates>
                </div>
                    
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="//rev:CommentForEditor" mode="handle-emphasis">
        <xsl:value-of select="current()"/>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Italic"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Underline"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Bold"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/rev:List"></xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="//rev:CommentForAuthor" mode="handle-emphasis">
        <xsl:for-each select="self::node()/rev:EditSuggestion">  
            <xsl:apply-templates select="self::node()"></xsl:apply-templates>        
        </xsl:for-each>
    </xsl:template>
   
    <xsl:template match="//rev:EditSuggestion" >
        <xsl:value-of select="self::node()/text()"/>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Italic"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Underline"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/pbl:Style/pbl:Bold"></xsl:apply-templates>
        <xsl:apply-templates select="self::node()/rev:List"></xsl:apply-templates>
    </xsl:template>
    
    
    <xsl:template match="//rev:List">
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
    

</xsl:stylesheet>