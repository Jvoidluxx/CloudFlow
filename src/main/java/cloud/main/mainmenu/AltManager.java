package cloud.main.mainmenu;

import cloud.main.utils.RenderUtil;
import cloud.main.utils.fontutils.FontRenderer;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;

import java.awt.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AltManager extends GuiScreen {
    //davidne was here bleehhh bad code 1/10 client ong


    // Made By DavidNe -Jvoidluxx
    // edited a bit by jvoid

    // README : For any issues from the Microsoft shit, use this link : https://github.com/Litarvan/OpenAuth
    // download the library and put it in a folder called lib, then add the library in project settings

    private FontRenderer fr = new FontRenderer("Product Sans Bold", 15, Font.PLAIN, true, true);
    private GuiTextField emailField;
    private GuiTextField passwordField;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawBoxes();
        RenderUtil.drawHorizontalLine(0, 300, 1000, 1, new Color(86, 154, 229, 250).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }



    public void drawBoxes() {
        fr.drawString("Logged in as " + mc.session.getUsername(),(float) width / 2,(float) height / 2 + 50, new Color(224, 234, 245, 250).getRGB());
        emailField.drawCustomTextBox(fr);
        passwordField.drawCustomTextBox(fr);
    }

    @Override
    public void initGui() {
        int x = width/2;
        int y = height/2;
        emailField = new GuiTextField(0, fontRendererObj, x + 50, y + 70, 80, 20);
        emailField.setMaxStringLength(255);
        emailField.setFocused(false);

        //RenderUtil.drawRectBetter(x, y, x+y, );
        passwordField = new GuiTextField(1, fontRendererObj, x - 50, y + 70, 80, 20);
        passwordField.setMaxStringLength(255);
        passwordField.setFocused(false);

        buttonList.add(new GuiButton(0, x -  55,y + 100, "Login"));

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            boolean emptyEmail = emailField.getText().isEmpty();
            boolean emptyPassword = passwordField.getText().isEmpty();
            LoginType type = LoginType.CRACKED;
            if(emptyEmail && emptyPassword){
                return;
            }
            if(!emptyEmail && !emptyPassword) {
                type = LoginType.MICROSOFT;
            }

            login(type, emailField.getText(), passwordField.getText());
        }

        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        emailField.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    public void login(LoginType type, String user, String password) {
        switch (type) {
            case CRACKED:
                mc.session = new Session(user, user, "0", "legacy");
                break;

            case MICROSOFT:
                new Thread(() ->
                {
                    try {
                        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                        MicrosoftAuthResult result = authenticator.loginWithCredentials(user, password);
                        mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
                    } catch (MicrosoftAuthenticationException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
        }
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (emailField.isFocused()) {
            emailField.textboxKeyTyped(typedChar, keyCode);
        } else if (passwordField.isFocused()) {
            passwordField.textboxKeyTyped(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }
    enum LoginType {
        CRACKED,
       MICROSOFT,
   }
}
