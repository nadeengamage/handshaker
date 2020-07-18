/*
 * Copyright (c) Nadeen Gamage. (https://www.nadeengamage.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */

import enums.Messages;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enums.Color.RED;
import static enums.Color.RESET;
import static enums.Messages.*;

public class Reader {

    private final static Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8.name());

    private String certificatePath;

    private String domainName;

    /*
    * Populate the data.
    *
    * */
    public void load() {

        while (certificatePath == null || domainName == null) {

            // load the certificate path
            if (certificatePath == null) {
                System.out.println(Messages.ENTER_CERTIFICATE.value());
                String path = SCANNER.nextLine();

                if (path.isEmpty()) continue;

                File file = new File(path);
                // file validator
                if (file.isFile() && fileValidator(file)) {
                    this.setCertificatePath(file.getAbsolutePath());
                } else {
                    System.out.print(RED.value());
                    System.out.println(ENTER_VALID_PATH.value());
                    System.out.print(RESET.value());
                }
            } else {
                System.out.println(ENTER_DOMAIN_NAME.value());
                String name = SCANNER.next();

                if (name.isEmpty()) continue;

                // validate the domain name
                if (domainValidator(name)) {
                    name = name.replaceFirst("www.", "");
                    this.setDomainName(name);
                }
            }
        }
    }

    public void splash() {
        String splashText = "\n" +
                "  _    _                 _     _           _             \n" +
                " | |  | |               | |   | |         | |            \n" +
                " | |__| | __ _ _ __   __| |___| |__   __ _| | _____ _ __ \n" +
                " |  __  |/ _` | '_ \\ / _` / __| '_ \\ / _` | |/ / _ \\ '__|\n" +
                " | |  | | (_| | | | | (_| \\__ \\ | | | (_| |   <  __/ |   \n" +
                " |_|  |_|\\__,_|_| |_|\\__,_|___/_| |_|\\__,_|_|\\_\\___|_|   \n" +
                " Developer - Nadeen Gamage | v1.0.0                        \n" +
                "                                                         \n";

        System.out.println(splashText);
    }

    private boolean fileValidator(File file) {
        String fileName = file.getName();
        return fileName.endsWith(".cer");
    }

    private boolean domainValidator(String domainName) {
        String regex = "\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(domainName);
        if (matcher.lookingAt()) {
            return true;
        } else {
            System.out.print(RED.value());
            System.out.println(ENTER_VALID_DOMAIN_NAME.value());
            System.out.print(RESET.value());
            return false;
        }
    }

    public File getCertificatePath() {
        return new File(this.certificatePath);
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
