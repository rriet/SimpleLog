<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
	<xsl:template match="report">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="left"
					page-height="210mm" page-width="297mm" margin-top="10mm"
					margin-bottom="10mm" margin-left="5mm" margin-right="32mm">
					<fo:region-body />
				</fo:simple-page-master>

				<fo:simple-page-master master-name="right"
					page-height="210mm" page-width="297mm" margin-top="10mm"
					margin-bottom="10mm" margin-left="32mm" margin-right="5mm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="left">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="25pt">
						SimpleLog Report
					</fo:block>
				</fo:flow>
			</fo:page-sequence>

			<xsl:for-each select="pages">
				<fo:page-sequence master-reference="left">
					<fo:flow flow-name="xsl-region-body">
						<fo:block font-size="7pt" text-align="center"
							display-align="center">
							<fo:table table-layout="fixed" width="260mm"
								border-collapse="separate" border="solid 0.1mm black">

								<fo:table-column column-width="20mm" />
								<fo:table-column column-width="10mm" />
								<fo:table-column column-width="10mm" />
								<fo:table-column column-width="10mm" />
								<fo:table-column column-width="10mm" />
								<fo:table-column column-width="26mm" />
								<fo:table-column column-width="26mm" />
								<fo:table-column column-width="13mm" />
								<fo:table-column column-width="13mm" />
								<fo:table-column column-width="20mm" />
								<fo:table-column column-width="20mm" />
								<fo:table-column column-width="52mm" />
								<fo:table-column column-width="7.5mm" />
								<fo:table-column column-width="7.5mm" />
								<fo:table-column column-width="7.5mm" />
								<fo:table-column column-width="7.5mm" />

								<fo:table-header>
									<fo:table-row height="4mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>1</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>2</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>3</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>4</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="3">
											<fo:block>5</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>6</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>7</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="4">
											<fo:block>8</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="6mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2" display-align="center">
											<fo:block>DATE</fo:block>
											<fo:block>(DD/MM/YYYY)</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>DEPARTURE</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>ARRIVAL</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>AIRCRAFT</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>SINGLE PILOT TIME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2" display-align="center">
											<fo:block>MULTI-PILOT</fo:block>
											<fo:block>TIME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2" display-align="center">
											<fo:block>TOTAL TIME</fo:block>
											<fo:block>OF FLIGHT</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2" display-align="center">
											<fo:block>PIC NAME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>TAKEOFFS</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>LANDINGS</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="10mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>PLACE</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>TIME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>PLACE</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>TIME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>MAKE, MODEL, VARIANT</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>REGISTRATION</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>SEL</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>MEL</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>D</fo:block>
											<fo:block>A</fo:block>
											<fo:block>Y</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											display-align="center">
											<fo:block>N</fo:block>
											<fo:block>I</fo:block>
											<fo:block>G</fo:block>
											<fo:block>H</fo:block>
											<fo:block>T</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											display-align="center">
											<fo:block>D</fo:block>
											<fo:block>A</fo:block>
											<fo:block>Y</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											display-align="center">
											<fo:block>N</fo:block>
											<fo:block>I</fo:block>
											<fo:block>G</fo:block>
											<fo:block>H</fo:block>
											<fo:block>T</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-footer>
									<fo:table-row height="5mm">
										<fo:table-cell number-columns-spanned="7"
											border-left="solid 0.1mm black" padding-right="2mm"
											border-right="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block text-align="right">
												TOTAL THIS PAGE
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/selTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/melTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/multiPilotTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/totalTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/takeOffDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/takeOffNight" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/landingDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/landingNight" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm">
										<fo:table-cell number-columns-spanned="7"
											border-left="solid 0.1mm black" padding-right="2mm"
											border-right="solid 0.1mm black">
											<fo:block text-align="right">
												TOTAL FROM PREVIOUS PAGES
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/selTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/melTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/multiPilotTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/totalTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsBefore/takeOffDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/takeOffNight" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsBefore/landingDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/landingNight" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm">
										<fo:table-cell number-columns-spanned="7"
											border-left="solid 0.1mm black"
											border-right="solid 0.1mm black"
											border-bottom="solid 0.1mm black" padding-right="2mm">
											<fo:block text-align="right">
												TOTAL TIME
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/selTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/melTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/multiPilotTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/totalTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsAfter/takeOffDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsAfter/takeOffNight" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsAfter/landingDay" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of select="totalsAfter/landingNight" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-footer>

								<fo:table-body>
									<xsl:for-each select="reportLine">
										<fo:table-row height="5.3mm"
											display-align="center">
											
											<xsl:if test="position() mod 2">
								                <xsl:attribute name="background-color">
								                    <xsl:text>#EEF0F2</xsl:text>
								                </xsl:attribute>
								            </xsl:if>
											
											<xsl:choose>
												<xsl:when test="aircraft/simulator = 'true'">
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="departureDateString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>

												</xsl:when>
												<xsl:otherwise>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="departureDateString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="departureAirport/icao" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="departureTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="arrivalAirport/icao" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="arrivalTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of
																select="aircraft/aircraftModel/modelName" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="aircraft/registration" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="selTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="melTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="multiPilotTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="totalTimeString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black"
														padding-left="2mm">
														<fo:block text-align="left">
															<xsl:value-of select="crewPic/crew_name" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="takeOffDayString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="takeOffNightString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="landingDayString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="landingNightString" />
														</fo:block>
													</fo:table-cell>



												</xsl:otherwise>
											</xsl:choose>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>
						<fo:block font-size="7pt" padding-top="2mm">
							PAGE
							<xsl:value-of select="pageNumber" />
							A
						</fo:block>
					</fo:flow>
				</fo:page-sequence>

				<fo:page-sequence master-reference="right">
					<fo:flow flow-name="xsl-region-body">
						<fo:block font-size="7pt" text-align="center"
							display-align="center">
							<fo:table table-layout="fixed" width="260mm"
								border-collapse="separate" border="solid 0.1mm black">

								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="26mm" />
								<fo:table-column column-width="18mm" />
								<fo:table-column column-width="72mm" />

								<fo:table-header>
									<fo:table-row height="4mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>9</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="5">
											<fo:block>10</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="3">
											<fo:block>11</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>12</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="6mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>OPERATIONAL CONDITION TIME
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="5">
											<fo:block>PILOT FUNCTION TIME</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="3">
											<fo:block>SYNTETIC TRAINING DEVICES
												SESSION
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="3">
											<fo:block>REMARKS</fo:block>
											<fo:block>AND ENDORSEMENTS</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>NIGHT</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>IFR</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-columns-spanned="2">
											<fo:block>PILOT IN COMMAND</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>CO-PILOT</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>DUAL</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>INSTRUCTOR</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>DATE</fo:block>
											<fo:block>(DD/MM/YYYY)</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>TYPE</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											number-rows-spanned="2">
											<fo:block>TOTAL TIME</fo:block>
											<fo:block>OF SESSION</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm" display-align="center">
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>P1</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>P1 U/S</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-footer>
									<fo:table-row height="5mm">
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/nightTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/ifrTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/picTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/picusTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of select="totalsPage/sicTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/dualTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/instructorTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black">
											<fo:block>
												<xsl:value-of
													select="totalsPage/fstdTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black"
											border-top="solid 0.7mm black" number-rows-spanned="3">
											<fo:block-container height="8mm">
												<fo:block>I certify that the entries in this log are true
												</fo:block>
											</fo:block-container>
											<fo:block>_________________________________</fo:block>
											<fo:block>PILOT'S SIGNATURE</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm">
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/nightTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/ifrTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/picTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/picusTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/sicTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/dualTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/instructorTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsBefore/fstdTimeString" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row height="5mm">
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/nightTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/ifrTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/picTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/picusTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/sicTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/dualTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/instructorTimeString" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block></fo:block>
										</fo:table-cell>
										<fo:table-cell border="solid 0.1mm black">
											<fo:block>
												<xsl:value-of
													select="totalsAfter/fstdTimeString" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-footer>


								<fo:table-body>
									<xsl:for-each select="reportLine">
										<fo:table-row height="5.3mm"
											display-align="center">
											
											<xsl:if test="position() mod 2">
								                <xsl:attribute name="background-color">
								                    <xsl:text>#EEF0F2</xsl:text>
								                </xsl:attribute>
								            </xsl:if>
											
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="nightTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="ifrTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="picTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="picusTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="sicTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="dualTimeString" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid 0.1mm black">
												<fo:block>
													<xsl:value-of select="instructorTimeString" />
												</fo:block>
											</fo:table-cell>

											<xsl:choose>
												<xsl:when test="aircraft/simulator = 'true'">
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="departureDateString" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of
																select="aircraft/aircraftModel/modelName" />
															(
															<xsl:value-of select="aircraft/registration" />
															)

														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block>
															<xsl:value-of select="fstdTimeString" />
														</fo:block>
													</fo:table-cell>
												</xsl:when>
												<xsl:otherwise>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell border="solid 0.1mm black">
														<fo:block></fo:block>
													</fo:table-cell>
												</xsl:otherwise>
											</xsl:choose>

											<fo:table-cell border="solid 0.1mm black"
												padding-left="2mm">
												<fo:block text-align="left">
													<xsl:value-of select="remarks" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>
						<fo:block font-size="7pt" text-align="right"
							padding-top="2mm">
							PAGE
							<xsl:value-of select="pageNumber" />
							B
						</fo:block>
					</fo:flow>
				</fo:page-sequence>
			</xsl:for-each>

		</fo:root>
	</xsl:template>
</xsl:stylesheet>