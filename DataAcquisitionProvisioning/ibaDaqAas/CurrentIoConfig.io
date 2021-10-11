<?xml version="1.0" encoding="utf-8"?>
<IOConfiguration version="5">
  <General>
    <SlowTimebase>100000</SlowTimebase>
    <FobMTimebase>1000</FobMTimebase>
    <PCIInterruptBusNr>0</PCIInterruptBusNr>
    <PCIInterruptSlotNr>0</PCIInterruptSlotNr>
    <TCPAliveTime>10</TCPAliveTime>
    <EnglishS7Operands>0</EnglishS7Operands>
    <UnloadS7AddressbooksDuringAcquisition>0</UnloadS7AddressbooksDuringAcquisition>
    <UnloadTwinCATAddressbooksDuringAcquisition>0</UnloadTwinCATAddressbooksDuringAcquisition>
    <AutoStartOnStartup>0</AutoStartOnStartup>
    <AutoStartOnStartupDelay>0</AutoStartOnStartupDelay>
    <AutoRestartOnError>0</AutoRestartOnError>
    <AutoDisableModulesOnBrokenFobFastLink>0</AutoDisableModulesOnBrokenFobFastLink>
    <DisableOPCItemWarnings>1</DisableOPCItemWarnings>
    <MaxOPCConnections>64</MaxOPCConnections>
    <InterruptBufferSize>20</InterruptBufferSize>
    <TCPClearOnDisconnect>0</TCPClearOnDisconnect>
    <FobFastBrokenLinkDetection>0</FobFastBrokenLinkDetection>
    <FobFastLinkReconnectDetection>1</FobFastLinkReconnectDetection>
    <FobFastBufferOverflowDetection>0</FobFastBufferOverflowDetection>
    <FobFastLinkBufferEmptyDetection>1</FobFastLinkBufferEmptyDetection>
    <FobFastReportLinkStatus>1</FobFastReportLinkStatus>
    <FOBFPCIBUFCollectDataAsync>0</FOBFPCIBUFCollectDataAsync>
    <FobMBrokenLinkDetection>0</FobMBrokenLinkDetection>
    <FobMLinkReconnectDetection>1</FobMLinkReconnectDetection>
    <FobMBufferOverflowDetection>0</FobMBufferOverflowDetection>
    <FobMLinkBufferEmptyDetection>1</FobMLinkBufferEmptyDetection>
    <FOBMPCICollectDataAsync>0</FOBMPCICollectDataAsync>
    <FobDBrokenLinkDetection>0</FobDBrokenLinkDetection>
    <FobDLinkReconnectDetection>0</FobDLinkReconnectDetection>
    <FobDLinkBufferEmptyDetection>0</FobDLinkBufferEmptyDetection>
    <FobDClearValues>0</FobDClearValues>
    <FobDIpAddressPrefix>172.29.</FobDIpAddressPrefix>
    <FlexUDPPort>62012</FlexUDPPort>
    <HideEmptySlots>0</HideEmptySlots>
    <FM458RestartDetection>0</FM458RestartDetection>
    <S7DpmsRestartDetection>1</S7DpmsRestartDetection>
    <M1AutoRestart>0</M1AutoRestart>
    <FobUsbInterrupt>0</FobUsbInterrupt>
    <HPCiTOCSettings>
      <AddressbookPath>C:\HPCi\AddressBooks</AddressbookPath>
      <AddressbookUserName></AddressbookUserName>
      <AddressbookPassword></AddressbookPassword>
      <SyncTime>0</SyncTime>
      <TimeSyncConvertToLocal>1</TimeSyncConvertToLocal>
    </HPCiTOCSettings>
    <DCF77>
      <Active_0>0</Active_0>
      <Signal_0>[65534:259543]</Signal_0>
      <Polarity_0>0</Polarity_0>
      <UtcTime_0>0</UtcTime_0>
      <CompensateDST_0>0</CompensateDST_0>
      <Active_1>0</Active_1>
      <Signal_1>[65534:259543]</Signal_1>
      <Polarity_1>0</Polarity_1>
      <UtcTime_1>0</UtcTime_1>
      <CompensateDST_1>0</CompensateDST_1>
      <SyncOnSeconds>0</SyncOnSeconds>
    </DCF77>
    <RemoteConfigSettings>
      <Active>0</Active>
      <FileName></FileName>
      <UserName></UserName>
      <Password></Password>
    </RemoteConfigSettings>
    <TCPWatchdogSettings>
      <Enabled>0</Enabled>
      <CycleTime>10</CycleTime>
      <PortNr>40001</PortNr>
      <Address></Address>
      <ActiveNode>0</ActiveNode>
      <UDP>0</UDP>
      <Binary>1</Binary>
    </TCPWatchdogSettings>
    <TimeSync>
      <Version>2</Version>
      <Active>0</Active>
      <UnixTimeSignal>[65534:259543]</UnixTimeSignal>
      <MicrosecTimeSignal>[65534:259543]</MicrosecTimeSignal>
      <ConvertToLocal>0</ConvertToLocal>
    </TimeSync>
    <PTPSettings>
      <Enabled>0</Enabled>
      <NdisInterfaceName></NdisInterfaceName>
      <Domain>0</Domain>
      <UseLayer2>0</UseLayer2>
      <UseP2PDelay>0</UseP2PDelay>
      <Ai>9.9999997473787516E-05</Ai>
      <Ap>0.019999999552965164</Ap>
    </PTPSettings>
    <PTPMasterSettings>
      <Enabled>0</Enabled>
      <NdisInterfaceName></NdisInterfaceName>
      <Domain>0</Domain>
      <UseLayer2>0</UseLayer2>
      <UseP2PDelay>0</UseP2PDelay>
      <Ai>9.9999997473787516E-05</Ai>
      <Ap>0.019999999552965164</Ap>
    </PTPMasterSettings>
    <TimeSyncMode>0</TimeSyncMode>
    <MultiStation>
      <Mode>0</Mode>
      <StartTimeout>30</StartTimeout>
      <PortNr>9175</PortNr>
      <EnableUnSyncedStations>0</EnableUnSyncedStations>
      <UnSyncedLocalIp></UnSyncedLocalIp>
      <UnSyncedMulticastIp>226.227.228.100</UnSyncedMulticastIp>
      <UnSyncedMulticastPort>9176</UnSyncedMulticastPort>
      <UnSyncedMulticastTtl>1</UnSyncedMulticastTtl>
      <Stations />
    </MultiStation>
    <PhysicalLocations InUse="0" Prefix="1" Version="2" NrSlots="16" />
    <SNMPConfig>
      <Enable>0</Enable>
      <StartAcquisitionOnError>1</StartAcquisitionOnError>
      <PublishAll>0</PublishAll>
      <DescriptionMode>1</DescriptionMode>
      <PortNr>1611</PortNr>
      <EventDuration>5000</EventDuration>
      <CodePage>1252</CodePage>
      <CommunityString>public</CommunityString>
      <User>
        <UserName>public</UserName>
        <Password>12345678</Password>
        <EncryptionKey>12345678</EncryptionKey>
        <AuthAlgorithm>0</AuthAlgorithm>
        <EncrAlgorithm>0</EncrAlgorithm>
      </User>
      <CustomOids />
    </SNMPConfig>
    <OpcUAServerConfig>
      <Enable>0</Enable>
      <StartAcquisitionOnError>1</StartAcquisitionOnError>
      <PublishAll>0</PublishAll>
      <DescriptionMode>1</DescriptionMode>
      <CertificateThumbprint></CertificateThumbprint>
      <AllowAnonymous>0</AllowAnonymous>
      <AllowUserPass>1</AllowUserPass>
      <AllowCertificate>0</AllowCertificate>
      <AllowedUsers>
        <AllowedUser>
          <Username>pda</Username>
          <Password>0x7C46D2</Password>
        </AllowedUser>
      </AllowedUsers>
      <SecurityNone>0</SecurityNone>
      <SecurityBasic128>0</SecurityBasic128>
      <SecurityBasic256>0</SecurityBasic256>
      <SecurityBasic256Sha256>3</SecurityBasic256Sha256>
      <Endpoints>
        <Endpoint>opc.tcp://nb-601:48080</Endpoint>
      </Endpoints>
      <CustomTags />
    </OpcUAServerConfig>
    <IEC61850ServerConfig Version="1">
      <Name>IBAPDA_IEC_61850_SERVER</Name>
      <Enable>0</Enable>
      <StartAcquisitionOnError>0</StartAcquisitionOnError>
      <PortNr>102</PortNr>
      <Password></Password>
      <LogicalDevice Name="ibaPDA" Rights="34">
        <LogicalNode Name="LLN0" Type="1" Rights="0">
          <CDC BaseName="Mod" InstanceNr="0" Type="4" Rights="0">
            <Attribute Name="stVal" Type="2" Rights="0" FC="0" TriggerOptions="1">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue>1</DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="q" Type="4" Rights="0" FC="0" TriggerOptions="2" />
            <Attribute Name="t" Type="5" Rights="0" FC="0" TriggerOptions="0" />
            <Attribute Name="ctlModel" Type="8" Rights="0" FC="4" TriggerOptions="0">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue>1</DefaultValue>
              </DynamicValue>
            </Attribute>
          </CDC>
          <CDC BaseName="Beh" InstanceNr="0" Type="5" Rights="0">
            <Attribute Name="stVal" Type="2" Rights="0" FC="0" TriggerOptions="1">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue>1</DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="q" Type="4" Rights="0" FC="0" TriggerOptions="2" />
            <Attribute Name="t" Type="5" Rights="0" FC="0" TriggerOptions="0" />
          </CDC>
          <CDC BaseName="Health" InstanceNr="0" Type="5" Rights="0">
            <Attribute Name="stVal" Type="2" Rights="0" FC="0" TriggerOptions="1">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue>1</DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="q" Type="4" Rights="0" FC="0" TriggerOptions="2" />
            <Attribute Name="t" Type="5" Rights="0" FC="0" TriggerOptions="0" />
          </CDC>
          <CDC BaseName="NamPlt" InstanceNr="0" Type="6" Rights="0">
            <Attribute Name="vendor" Type="6" Rights="0" FC="5" TriggerOptions="0">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue>iba AG</DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="swRev" Type="6" Rights="0" FC="5" TriggerOptions="0">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue></DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="ldNs" Type="6" Rights="0" FC="5" TriggerOptions="0">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue></DefaultValue>
              </DynamicValue>
            </Attribute>
            <Attribute Name="configRev" Type="6" Rights="0" FC="5" TriggerOptions="0">
              <DynamicValue>
                <ChannelId>[65534:259543]</ChannelId>
                <DefaultValue></DefaultValue>
              </DynamicValue>
            </Attribute>
          </CDC>
        </LogicalNode>
      </LogicalDevice>
    </IEC61850ServerConfig>
    <ProtectionData>0037F0A4D3F6E6A766D195DFD9FCA7DFED83D4AB84FBFE18</ProtectionData>
  </General>
  <PcInterfaces>
    <PcInterface id="0x8A000000" VersionCompat="7000001">
      <Name>OPC</Name>
      <Visible>1</Visible>
      <AllowInaccessibleServers>0</AllowInaccessibleServers>
      <ConnectTimeout>-30</ConnectTimeout>
      <ClearOutputsOnStop>0</ClearOutputsOnStop>
      <IgnoreUnavailableTags>0</IgnoreUnavailableTags>
      <ConnectionPerModule>0</ConnectionPerModule>
      <Links>
        <Link id="0x8A000000" />
        <OutputLink id="0x8A040000" />
      </Links>
    </PcInterface>
    <PcInterface id="0x8B000000" VersionCompat="7000001">
      <Name>Virtual</Name>
      <Visible>1</Visible>
      <Links>
        <Link id="0x8B000000" />
      </Links>
    </PcInterface>
    <PcInterface id="0x9F000000" VersionCompat="7000001">
      <Name>ibaCapture</Name>
      <Visible>1</Visible>
      <AllowInaccessibleServers>0</AllowInaccessibleServers>
      <AutoRestartOnNewCamConfig>1</AutoRestartOnNewCamConfig>
      <Links>
        <Link id="0x9F000000" />
        <OutputLink id="0x9F040000" />
      </Links>
      <RenameConfig>
        <Active>0</Active>
        <FileName>c:\ibaCapture-CAM\camera_config_IBA.xml</FileName>
        <UserName></UserName>
        <Password></Password>
      </RenameConfig>
    </PcInterface>
    <PcInterface id="0xA3000000" VersionCompat="7000001">
      <Name>Playback</Name>
      <Visible>1</Visible>
      <Active>1</Active>
      <FileName>C:\Users\dapo01\Desktop\Package_BaSys\HRM_Product_0001691.dat</FileName>
      <UserName></UserName>
      <Password></Password>
      <FilePassword></FilePassword>
      <Begin>0</Begin>
      <End>-600000000</End>
      <SpeedFactor>1</SpeedFactor>
      <BufferSize>500</BufferSize>
      <Links>
        <Link id="0xA3000000" />
      </Links>
    </PcInterface>
    <PcInterface id="0xB2000000" VersionCompat="7000001">
      <Name>E-mail</Name>
      <Visible>1</Visible>
      <Links>
        <OutputLink id="0xB2040000" />
      </Links>
      <Accounts>
        <DefaultAccount>00000000-0000-0000-0000-000000000000</DefaultAccount>
      </Accounts>
      <Fields />
    </PcInterface>
    <PcInterface id="0xC5000000" VersionCompat="7000001">
      <Name>TextInterface</Name>
      <Visible>1</Visible>
      <Links>
        <Link id="0xC5000000" />
      </Links>
    </PcInterface>
  </PcInterfaces>
  <Modules>
    <Module>
      <Name>ibaLogic - Hydr. Adjustement</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>0</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>28</NrAnalogSignals>
      <NrDigitalSignals>0</NrDigitalSignals>
      <FileModuleNr>0</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000000</InterfaceLink>
          <Analog>
            <Signal>
              <Name>Pos_FS1InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F1 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 1 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:0]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS1OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F1 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 1 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:1]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS1InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F1 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 1 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:2]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS1OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F1 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 1 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:3]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS2InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F2 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 2 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:4]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS2OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F2 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 2 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:5]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS2InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F2 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 2 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:6]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS2OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F2 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 2 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:7]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS3InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F3 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 3 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:8]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS3OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F3 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 3 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:9]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS3InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F3 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 3 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:10]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS3OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F3 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 3 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:11]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS4InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F4 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 4 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:12]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS4OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F4 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 4 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:13]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS4InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F4 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 4 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:14]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS4OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F4 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 4 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:15]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS5InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F5 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 5 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:16]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS5OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F5 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 5 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:17]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS5InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F5 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 5 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:18]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS5OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F5 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 5 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:19]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS6InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F6 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 6 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:20]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS6OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F6 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 6 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:21]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS6InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F6 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 6 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:22]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS6OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F6 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 6 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:23]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS7InletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F7 Weg AS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 7 Inlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:24]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS7OutletAS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F7 Weg AS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 7 Outlet A-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:25]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS7InletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F7 Weg BS Einlauf</Comment1>
              <Comment2>Position Finishing Mill Stand 7 Inlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:26]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Pos_FS7OutletBS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>F7 Weg BS Auslauf</Comment1>
              <Comment2>Position Finishing Mill Stand 7 Outlet B-Side (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[0:27]</FileSignalId>
            </Signal>
          </Analog>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaLogic - Shear / Stand 1-7</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>1</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>31</NrAnalogSignals>
      <NrDigitalSignals>30</NrDigitalSignals>
      <FileModuleNr>1</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000001</InterfaceLink>
          <Analog>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS1Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 1 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 1 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:2]</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS2Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 2 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 2 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:3]</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS3Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 3 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 3 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:4]</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS4Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 4 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 4 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:5]</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS5Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 5 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 5 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:6]</FileSignalId>
            </Signal>
            <Signal>
              <Name>SlgAngle_FS6Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Schlingenwinkel Fertigstraßengerüst 6 (IST)</Comment1>
              <Comment2>Sling Angle Finishing Mill Stand 6 Outlet (Actual value)</Comment2>
              <Unit>Grad</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:7]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>Speed_Shear_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Geschwindigkeit Schere (SOLL)</Comment1>
              <Comment2>Speed shear (Set value)</Comment2>
              <Unit>m/s</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:13]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>Speed_Shear-LatGuide_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Geschwindigkeit Seitenführung Schere (IST)</Comment1>
              <Comment2>Speed Lateral guide shear (Actual value)</Comment2>
              <Unit>U/min</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:15]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>Speed_Shear_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Geschwindigkeit Schere (IST)</Comment1>
              <Comment2>Speed shear (Actual value)</Comment2>
              <Unit>m/s</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:18]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Speed_Material-FS7_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Materialgeschwindigkeit nach  Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Material speed  Finishing Mill Stand 7 Outlet (Actual value)</Comment2>
              <Unit>m/s</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:19]</FileSignalId>
            </Signal>
            <Signal>
              <Name>AI_Shear_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Ankerstrom Schere (IST)</Comment1>
              <Comment2>Armature current shear (Actual value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:20]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS1-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 1 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 1 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:21]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS2-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 2 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 2 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:22]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS3-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 3 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 3 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:23]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS4-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 4 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 4 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:24]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS5-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 5 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 5 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:25]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS6-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 6 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 6 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:26]</FileSignalId>
            </Signal>
            <Signal>
              <Name>DeltaPos_FS7-AGC_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Positionsänderung AGC Fertigstraßengerüst 7 (SOLL)</Comment1>
              <Comment2>Delta position Finishing Mill Stand 7 AGC (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:27]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS1_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 1 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 1 AGC (Set value)</Comment2>
              <Unit>A</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:29]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS3_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 3 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 3 AGC (Set value)</Comment2>
              <Unit>A</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[1:30]</FileSignalId>
            </Signal>
          </Analog>
          <Digital>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>MeasRollerTop_Valid</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Messrolle unten</Comment1>
              <Comment2>Measuring roller top (Valid)</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[1.28]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MeasRollerBottom_Valid</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Messrolle oben</Comment1>
              <Comment2>Measuring roller bottom (Valid)</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[1.29]</FileSignalId>
            </Signal>
          </Digital>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaLogic - Stand 1-7 / Rolling forces</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>2</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>32</NrAnalogSignals>
      <NrDigitalSignals>0</NrDigitalSignals>
      <FileModuleNr>2</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000002</InterfaceLink>
          <Analog>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS1_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 1 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 1 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:4]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS1_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 1 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 1 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:5]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS2_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 2 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 2 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:6]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS2_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 2 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 2 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:7]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS3_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 3 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 3 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:8]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS3_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 3 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 3 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:9]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS4_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 4 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 4 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:10]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS4_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 4 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 4 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:11]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS5_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 5 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 5 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:12]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS5_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 5 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 5 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:13]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS6_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 6 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 6 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:14]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS6_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 6 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 6 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:15]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS7_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl  Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 7 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:16]</FileSignalId>
            </Signal>
            <Signal>
              <Name>I_FS7_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom  Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Current Finishing Mill Stand 7 (Actual Value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:17]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS1AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 1 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 1 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:18]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS1BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 1 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 1 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:19]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS2AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 2 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 2 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:20]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS2BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 2 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 2 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:21]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS3AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 3 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 3 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:22]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS3BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 3 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 3 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:23]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS4AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 4 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 4 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:24]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS4BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 4 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 4 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:25]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS5AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 5 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 5 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:26]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS5BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 5 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 5 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:27]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS6AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 6 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 6 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:28]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS6BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 6 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 6 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:29]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS7AS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 7 A-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 7 A-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:30]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForce_FS7BS_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft  Fertigstraßengerüst 7 B-Seite (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 7 B-Side (Actual value)</Comment2>
              <Unit>t</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[2:31]</FileSignalId>
            </Signal>
          </Analog>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaLogic - IBA-Logic</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>3</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>16</NrAnalogSignals>
      <NrDigitalSignals>28</NrDigitalSignals>
      <FileModuleNr>3</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000003</InterfaceLink>
          <Analog>
            <Signal>
              <Name>Rpm_FS1CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 1 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 1 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:0]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS2CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 2 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 2 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:1]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS3CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 3 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 3 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:2]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS4CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 4 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 4 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:3]</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS5CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 5 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 5 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:4]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>Rpm_FS6CompValue_Set</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Korrekturwert Drehzahl  Fertigstraßengerüst 6 (SOLL)</Comment1>
              <Comment2>Rpm Finishing mill Stand 6 Compensation value (Set value)</Comment2>
              <Unit>%</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:6]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatTemp_FS7Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Material Temperatur Auslaufbereich Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Material temperature Outlet Finishing Mill Stand 7 (Actual value)</Comment2>
              <Unit>°C</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:8]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatTemp_FS1Inlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Material Temperatur hinter Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Material temperature Inlet Finishing Mill Stand 1 (Actual value)</Comment2>
              <Unit>°C</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:10]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatCenterLineDev_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Mittenabweichung Band (IST)</Comment1>
              <Comment2>Material center line deviation (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:11]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatThicknDev_FS7Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Material Dickenabweichung Auslaufbereich Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Thickness deviation Outlet Finishing Mill Stand 7 (Actual value)</Comment2>
              <Unit>µm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:12]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatDeltaThickn_FS7Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Material Delta Bandbreite Auslaufbereich Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Delta Thickness Outlet Finishing Mill Stand 7 (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:13]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatWidth_FS7Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Materialbreite Auslaufbereich Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Material width Outlet Finishing Mill Stand 7 (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:14]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatThickn_FS7Outlet_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Materialdicke Auslaufbereich Fertigstraßengerüst 7 (IST)</Comment1>
              <Comment2>Material thickness Outlet Finishing Mill Stand 7 (Actual value)</Comment2>
              <Unit>mm</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[3:15]</FileSignalId>
            </Signal>
          </Analog>
          <Digital>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS1Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 1</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 1</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.3]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS2Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 2</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 2</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.4]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS3Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 3</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 3</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.5]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS4Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 4</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 4</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.6]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS5Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 5</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 5</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.7]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS6Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 6</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 6</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.8]</FileSignalId>
            </Signal>
            <Signal>
              <Name>PhoCellFS7Inlet_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Photozelle Einlauf Fertigstraßengerüst 7</Comment1>
              <Comment2>Photo Cell Finishing Mill Stand 7</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.9]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS1_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 1 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.11]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS2_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 2 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.12]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS3_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 3 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.13]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS4_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 4 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.14]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS5_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 5 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.15]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS6_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 6 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.16]</FileSignalId>
            </Signal>
            <Signal>
              <Name>FS7_LoadOn</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Fertigstraßengerüst 1 belegt</Comment1>
              <Comment2>Finishing Mill Stand 7 Load on</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.17]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatThicknFS1_Valid</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Materialdicke nach Fertigstraßengerüst 7 - Messung gültig</Comment1>
              <Comment2>Material Thickness Finishing Mill Stand 7 - Measurement valid</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.26]</FileSignalId>
            </Signal>
            <Signal>
              <Name>MatWhidthFS1_Valid</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Materialbreite nach Fertigstraßengerüst 7 - Messung gültig</Comment1>
              <Comment2>Material Width Finishing Mill Stand 7 - Measurement valid</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[3.27]</FileSignalId>
            </Signal>
          </Digital>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaLogic - Shear</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>4</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>1</NrAnalogSignals>
      <NrDigitalSignals>0</NrDigitalSignals>
      <FileModuleNr>4</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000004</InterfaceLink>
          <Analog>
            <Signal>
              <Name>Speed_Shear_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Geschwindigkeit Schere (IST)</Comment1>
              <Comment2>Speed shear (Acual value)</Comment2>
              <Unit>m/s</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[4:0]</FileSignalId>
            </Signal>
          </Analog>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaLogic - Virtuall</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>5</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>1</NrAnalogSignals>
      <NrDigitalSignals>1</NrDigitalSignals>
      <FileModuleNr>5</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000005</InterfaceLink>
          <Analog>
            <Signal>
              <Name>MatIdent</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Material IDENT Nummer</Comment1>
              <Comment2>Material IDENT number</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[5:0]</FileSignalId>
            </Signal>
          </Analog>
          <Digital>
            <Signal>
              <Name>MatIdent_NewCoilInLine</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Comment1>Neue Material IDENT Nummer kommt</Comment1>
              <Comment2>New Material IDENT number in Line</Comment2>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[5.0]</FileSignalId>
            </Signal>
          </Digital>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>Virtuell</Name>
      <ModuleType>5</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>6</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>0</NrAnalogSignals>
      <NrDigitalSignals>1</NrDigitalSignals>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0x8B000000</InterfaceLink>
          <Digital>
            <Signal>
              <Name>AlarmEvent</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <Expression>[8:0]&gt;1675</Expression>
            </Signal>
          </Digital>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaPDA - Virtual signals (Stand 1-7)</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>8</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>37</NrAnalogSignals>
      <NrDigitalSignals>0</NrDigitalSignals>
      <FileModuleNr>8</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000006</InterfaceLink>
          <Analog>
            <Signal>
              <Name>RollForceFS1_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 1 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 1 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:0]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS2_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 2 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 2 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:1]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS3_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 3 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 3 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:2]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS4_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 4 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 4 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:3]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS5_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 5 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 5 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:4]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS6_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 6 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 6 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:5]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS7_Act</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 7 Gesamt (IST)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 7 total (Actual value)</Comment2>
              <Unit>kN</Unit>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:6]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS1_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 1 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 1 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:10]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS2_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 2 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 2 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:11]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS3_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 3 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 3 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:12]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS4_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 4 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 4 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:13]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS5_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 5 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 5 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:14]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS6_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 6 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 6 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:15]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RollForceFS7_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Walzkraft Fertigstraßengerüst 7 Gesamt (MAX)</Comment1>
              <Comment2>Rollforce Finishing Mill Stand 7 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:16]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS1_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 1 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 1 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:20]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS2_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 2 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 2 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:21]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS3_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 3 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 3 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:22]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS4_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 4 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 4 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:23]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS5_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 5 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 5 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:24]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS6_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 6 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 6 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:25]</FileSignalId>
            </Signal>
            <Signal>
              <Name>RpmFS7_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Drehzahl Fertigstraßengerüst 7 Gesamt (MAX)</Comment1>
              <Comment2>Rpm Finishing Mill Stand 7 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:26]</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name></Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>dummy</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS1_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 1 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 1 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:30]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS2_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 2 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 2 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:31]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS3_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 3 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 3 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:32]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS4_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 4 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 4 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:33]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS5_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 5 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 5 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:34]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS6_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 6 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 6 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:35]</FileSignalId>
            </Signal>
            <Signal>
              <Name>IFS7_Max</Name>
              <DataType>2</DataType>
              <Offset>0</Offset>
              <Comment1>Strom Fertigstraßengerüst 7 Gesamt (MAX)</Comment1>
              <Comment2>Current Finishing Mill Stand 7 total (Max value)</Comment2>
              <Active>1</Active>
              <X1>32767</X1>
              <X2>-32768</X2>
              <VisualMin>0</VisualMin>
              <VisualMax>0</VisualMax>
              <FileSignalId>[8:36]</FileSignalId>
            </Signal>
          </Analog>
        </Link>
      </Links>
    </Module>
    <Module>
      <Name>ibaPDA - Virtual signals (Miscellaneous II)</Name>
      <ModuleType>98</ModuleType>
      <Timebase>100000</Timebase>
      <Enabled>1</Enabled>
      <Locked>0</Locked>
      <UseGlobalTimebase>1</UseGlobalTimebase>
      <ModuleNr>14</ModuleNr>
      <SwapMode>0</SwapMode>
      <SwapDigitals>0</SwapDigitals>
      <AsyncMode>0</AsyncMode>
      <Valid>1</Valid>
      <PrependModuleName>0</PrependModuleName>
      <NrAnalogSignals>0</NrAnalogSignals>
      <NrDigitalSignals>1</NrDigitalSignals>
      <FileModuleNr>14</FileModuleNr>
      <FileTimebase>100000</FileTimebase>
      <Links>
        <Link Name="">
          <Region>0</Region>
          <InterfaceLink>0xA3000007</InterfaceLink>
          <Digital>
            <Signal>
              <Name>CoilID trigger</Name>
              <DataType>0</DataType>
              <Offset>0</Offset>
              <Mask>1</Mask>
              <Active>1</Active>
              <FileSignalId>[14.0]</FileSignalId>
            </Signal>
          </Digital>
        </Link>
      </Links>
    </Module>
  </Modules>
  <InspectraProfiles />
  <Preprocessors />
  <LimitProfiles />
  <Groups />
</IOConfiguration>