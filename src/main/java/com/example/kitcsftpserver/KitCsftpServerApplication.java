package com.example.kitcsftpserver;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.io.IoServiceFactoryFactory;
import org.apache.sshd.mina.MinaServiceFactoryFactory;
import org.apache.sshd.scp.server.ScpCommandFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.config.keys.AuthorizedKeysAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

@SpringBootApplication
public class KitCsftpServerApplication {

    public static void main(String[] args)  {
        SpringApplication.run(KitCsftpServerApplication.class, args);
    }



//    @EventListener(ApplicationReadyEvent.class)
//    public void SFTP() throws IOException {
//        sftpServer.startSFTP();
//    }

    @EventListener(ApplicationReadyEvent.class)
    public void SFTPServer() throws IOException {
        System.setProperty(IoServiceFactoryFactory.class.getName(), MinaServiceFactoryFactory.class.getName());
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2222);

        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("host.ser").toPath()));

        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));

        sshd.setCommandFactory(new ScpCommandFactory());

        VirtualFileSystemFactory vfSysFactory = new VirtualFileSystemFactory();
        vfSysFactory.setDefaultHomeDir(new File("D:/TestFolder").toPath());
        sshd.setFileSystemFactory(vfSysFactory);

        sshd.setPasswordAuthenticator((username, password, session) ->
                username.equals("test") && password.equals("password"));

        sshd.setPublickeyAuthenticator(new AuthorizedKeysAuthenticator(new File("C:\\Users\\kites\\.ssh/authorized_keys").toPath()));

        sshd.start();
    }
}
