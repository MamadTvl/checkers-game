
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Gameplay implements EventHandler<MouseEvent> {

    @FXML
    private Pane Panel;
    private boolean[] Board;
    private final double distance = 130;
    private Circle[] marbleIcon;
    private Marbles[] marbles;
    private int CountBlack = 12, CountWhite = 12;
    private boolean Turn = true;
    private boolean MustHit = false;
    @FXML
    private Label cntBlack, cntWhite, WhoTurn;
    private Circle WLcircle, WRcircle, BLcircle, BRcircle;

    public void StartGame() {
        WhoTurn.setVisible(true);
        marbleIcon = new Circle[24];
        marbles = new Marbles[24];
        for (int i = 0; i < 12; i++) {
            marbleIcon[i] = new Circle(54.5);
            marbleIcon[i].setFill(Color.WHITE);
        }
        for (int i = 12; i < 24; i++)
            marbleIcon[i] = new Circle(54.5);
        double ex = 0, ey = 0;
        int j = 1;
        for (int i = 0; i < 12; i++) { // jw
            marbleIcon[i].setCenterX(727 + ex);
            marbleIcon[i].setCenterY(977 + ey);
            marbles[i] = new Marbles("White", j);
            ex += 2 * distance;
            j += 2;
            if ((i + 1) % 4 == 0) {
                if ((i + 1) == 4) {
                    ex = -1 * distance;
                    ey = -1 * distance;
                    j = 8;
                } else if ((i + 1) == 8) {
                    j = 17;
                    ex = 0;
                    ey = -2 * distance;
                }
            }
        }
        ex = 0;
        ey = 0;
        j = 56;
        for (int i = 12; i < 24; i++) {
            marbleIcon[i].setCenterX(597 + ex);
            marbleIcon[i].setCenterY(69 + ey);
            marbles[i] = new Marbles("Black", j);
            ex += 2 * distance;
            j += 2;
            if ((i + 1) % 4 == 0) {
                if ((i + 1) == 16) {
                    ex = distance;
                    ey = distance;
                    j = 49;
                } else if ((i + 1) == 20) {
                    ex = 0;
                    ey = 2 * distance;
                    j = 40;
                }

            }
        }
        for (int i = 0; i < 24; i++)// rooye panel
            Panel.getChildren().addAll(marbleIcon[i]);
        Board = new boolean[64];
        for (int i = 0; i < 24; i++) {
            if (i < 8 || i >= 16)
                Board[i] = i % 2 != 0;
            else Board[i] = i % 2 == 0;
        }
        for (int i = 24; i < 40; i++) {
            Board[i] = false;
        }
        for (int i = 40; i < 64; i++) {
            if (i > 48 && i < 56)
                Board[i] = i % 2 != 0;
            else Board[i] = i % 2 == 0;
        }
        for (int i = 0; i < 24; i++) {
            marbleIcon[i].setOnMouseClicked(this);
        }
    }

    public void Stop() {

        Panel.getChildren().removeAll(marbleIcon);
        Panel.getChildren().remove(WLcircle);
        Panel.getChildren().remove(WRcircle);
        Panel.getChildren().remove(BLcircle);
        Panel.getChildren().remove(BRcircle);
        marbleIcon = new Circle[24];
        WLcircle = new Circle(54.5);
        WRcircle = new Circle(54.5);
        BLcircle = new Circle(54.5);
        BRcircle = new Circle(54.5);
        marbles = new Marbles[24];
    }

    private void Play(int i) {
        SetMustHit(i);
        if (MustHit)
            Hiting(i);
        else
            Moving(i);
        Winner();
    }

    private void Moving(int i) {
        if (WLcircle != null)
            Panel.getChildren().remove(WLcircle);
        if (WRcircle != null)
            Panel.getChildren().remove(WRcircle);
        if (BLcircle != null)
            Panel.getChildren().remove(BLcircle);
        if (BRcircle != null)
            Panel.getChildren().remove(BRcircle);
        marbleIcon[i].setStroke(Color.TRANSPARENT);
        WLcircle = new Circle(54.5);
        WRcircle = new Circle(54.5);
        BLcircle = new Circle(54.5);
        BRcircle = new Circle(54.5);
        if (marbles[i].Color.equals("White") || marbles[i].King) {
            marbleIcon[i].setStroke(Color.rgb(255, 237, 0));
            if (marbles[i].getBoardCode() % 8 != 0 && marbles[i].getBoardCode() % 8 != 7) {// 7 15 23 31 39 47 55 63
                Right(i, true);
                Left(i, true);
            } else if (marbles[i].getBoardCode() % 8 == 0) {
                Right(i, true);
            } else if (marbles[i].getBoardCode() % 8 == 7) {
                Left(i, true);
            }
        }
        if (marbles[i].Color.equals("Black") || marbles[i].King) {
            if (marbles[i].getBoardCode() % 8 != 0 && marbles[i].getBoardCode() % 8 != 7) {// 7 15 23 31 39 47 55 63
                marbleIcon[i].setStroke(Color.rgb(255, 237, 0));
                Right(i, false);
                Left(i, false);
            } else if (marbles[i].getBoardCode() % 8 == 0) {
                marbleIcon[i].setStroke(Color.rgb(255, 237, 0));
                Right(i, false);
            } else if (marbles[i].getBoardCode() % 8 == 7) {
                marbleIcon[i].setStroke(Color.rgb(255, 237, 0));
                Left(i, false);
            }
        }
    }

    private void Hiting(int i) {
        Panel.getChildren().remove(WLcircle);
        Panel.getChildren().remove(WRcircle);
        Panel.getChildren().remove(BLcircle);
        Panel.getChildren().remove(BRcircle);
        WLcircle = new Circle(54.5);
        WRcircle = new Circle(54.5);
        BLcircle = new Circle(54.5);
        BRcircle = new Circle(54.5);
        if (marbles[i].Color.equals("White") && !marbles[i].King) {
            try {
                if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                        && Board[marbles[i].getBoardCode() + 8 - 1]
                        && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("Black")
                        && !Board[marbles[i].getBoardCode() + 2 * 8 - 2]) {
                    HitLeft(true, i);
                }
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                        && Board[marbles[i].getBoardCode() + 8 + 1]
                        && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("Black")
                        && !Board[marbles[i].getBoardCode() + 2 * 8 + 2]) {
                    HitRight(true, i);
                }

            } catch (ArrayIndexOutOfBoundsException ignore) {
            }

        }
        if (marbles[i].Color.equals("Black") && !marbles[i].King) {
            try {
                if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                        && Board[marbles[i].getBoardCode() - 8 - 1]
                        && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("White")
                        && !Board[marbles[i].getBoardCode() - 2 * 8 - 2]) {
                    HitLeft(false, i);
                }
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }

            try {
                if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                        && Board[marbles[i].getBoardCode() - 8 + 1]
                        && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("White")
                        && !Board[marbles[i].getBoardCode() - 2 * 8 + 2]) {
                    HitRight(false, i);
                }
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }

        }
        if (marbles[i].King) {
            if (marbles[i].Color.equals("White")) {
                try {
                    if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() + 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 - 2]) {
                        HitLeft(true, i);
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                try {
                    if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() + 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 + 2]) {
                        HitRight(true, i);
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                try {
                    if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() - 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 - 2]) {
                        HitLeft(false, i);
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                try {
                    if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() - 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 + 2]) {
                        HitRight(false, i);
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

            } else {
                try {
                    if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() + 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 - 2]) {
                        HitLeft(true, i);
                    }

                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                try {
                    if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() + 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 + 2]) {
                        HitRight(true, i);
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }
                try {
                    if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() - 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 - 2]) {
                        HitLeft(false, i);
                    }

                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                try {
                    if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() - 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 + 2]) {
                        HitRight(false, i);
                    }

                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

            }
        }
    }

    private void SetMustHit(int i) {
//------------------------------------------White Can Hit ?--------------------------------------------------------
        if (marbles[i].Color.equals("White") && !marbles[i].King) {
            // Hit Left
            try {
                if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                        && Board[marbles[i].getBoardCode() + 8 - 1]
                        && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("Black")
                        && !Board[marbles[i].getBoardCode() + 2 * 8 - 2])
                    MustHit = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {//Hit Right
                if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                        && Board[marbles[i].getBoardCode() + 8 + 1]
                        && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("Black")
                        && !Board[marbles[i].getBoardCode() + 2 * 8 + 2])
                    MustHit = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
//--------------------------------------------------------------------------------------------------------------------
        if (marbles[i].Color.equals("Black") && !marbles[i].King) {
            try {
                if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                        && Board[marbles[i].getBoardCode() - 8 - 1]
                        && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("White")
                        && !Board[marbles[i].getBoardCode() - 2 * 8 - 2])
                    MustHit = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                        && Board[marbles[i].getBoardCode() - 8 + 1]
                        && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("White")
                        && !Board[marbles[i].getBoardCode() - 2 * 8 + 2])
                    MustHit = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
//--------------------------------------------------------------------------------------------------------------------
        if (marbles[i].King) {
            if (marbles[i].Color.equals("White")) {
                try {
                    if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() + 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 - 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
                try {
                    if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() + 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 + 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() - 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 - 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() - 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("Black")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 + 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            } else {//black
                try {
                    if ((marbles[i].getBoardCode() + 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() + 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 - 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 - 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
                try {
                    if ((marbles[i].getBoardCode() + 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() + 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() + 8 + 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() + 2 * 8 + 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if ((marbles[i].getBoardCode() - 8 - 1) % 8 != 0
                            && Board[marbles[i].getBoardCode() - 8 - 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 - 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 - 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if ((marbles[i].getBoardCode() - 8 + 1) % 8 != 7
                            && Board[marbles[i].getBoardCode() - 8 + 1]
                            && marbles[FindNode(marbles[i].getBoardCode() - 8 + 1)].Color.equals("White")
                            && !Board[marbles[i].getBoardCode() - 2 * 8 + 2])
                        MustHit = true;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }
    }

    private void Left(int i, boolean color) {
        Panel.getChildren().remove(WLcircle);
        Panel.getChildren().remove(BLcircle);
        if (color || marbles[i].King) {
            try {
                if (!Board[marbles[i].getBoardCode() + 8 - 1]) {
                    WLcircle = new Circle(54.5);
                    WLcircle.setStroke(Color.rgb(255, 255, 255));
                    WLcircle.setFill(Color.TRANSPARENT);
                    WLcircle.setCenterX(marbleIcon[i].getCenterX() - distance);
                    WLcircle.setCenterY(marbleIcon[i].getCenterY() - distance);
                    Panel.getChildren().add(WLcircle);
                    WLcircle.setOnMouseClicked(event -> {
                        Board[marbles[i].getBoardCode()] = false;
                        marbles[i].GoLeftUp();
                        marbleIcon[i].setCenterX(WLcircle.getCenterX());
                        marbleIcon[i].setCenterY(WLcircle.getCenterY());
                        UpdateBoard(i, 0, false);
                        Turn = !Turn;
                        WLcircle.setVisible(false);
                        if (Turn)
                            WhoTurn.setText("White Turn");
                        else
                            WhoTurn.setText("Black Turn");
                    });
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
        if (!color || marbles[i].King) {
            try {
                if (!Board[marbles[i].getBoardCode() - 8 - 1]) {
                    BLcircle = new Circle(54.5);
                    BLcircle.setStroke(Color.rgb(255, 255, 255));
                    BLcircle.setFill(Color.TRANSPARENT);
                    BLcircle.setCenterX(marbleIcon[i].getCenterX() - distance);
                    BLcircle.setCenterY(marbleIcon[i].getCenterY() + distance);
                    Panel.getChildren().add(BLcircle);
                    BLcircle.setOnMouseClicked(event -> {
                        Board[marbles[i].getBoardCode()] = false;
                        marbles[i].GoLeftDown();
                        marbleIcon[i].setCenterX(BLcircle.getCenterX());
                        marbleIcon[i].setCenterY(BLcircle.getCenterY());
                        UpdateBoard(i, 0, false);
                        Turn = !Turn;
                        BLcircle.setVisible(false);
                        if (Turn)
                            WhoTurn.setText("White Turn");
                        else
                            WhoTurn.setText("Black Turn");
                    });
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    private void Right(int i, boolean color) {
        Panel.getChildren().remove(WRcircle);
        Panel.getChildren().remove(BRcircle);
        if (color || marbles[i].King) {
            try {
                if (!Board[marbles[i].getBoardCode() + 8 + 1]) {
                    WRcircle = new Circle(54.5);
                    WRcircle.setStroke(Color.rgb(255, 255, 255));
                    WRcircle.setFill(Color.TRANSPARENT);
                    WRcircle.setCenterX(marbleIcon[i].getCenterX() + distance);
                    WRcircle.setCenterY(marbleIcon[i].getCenterY() - distance);
                    Panel.getChildren().add(WRcircle);
                    WRcircle.setOnMouseClicked(event -> {
                        Board[marbles[i].getBoardCode()] = false;
                        marbles[i].GoRightUp();
                        marbleIcon[i].setCenterX(WRcircle.getCenterX());
                        marbleIcon[i].setCenterY(WRcircle.getCenterY());
                        UpdateBoard(i, 0, false);
                        Turn = !Turn;
                        WRcircle.setVisible(false);
                        if (Turn)
                            WhoTurn.setText("White Turn");
                        else
                            WhoTurn.setText("Black Turn");
                    });
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
        if (!color || marbles[i].King) {
            try {
                if (!Board[marbles[i].getBoardCode() - 8 + 1]) {
                    BRcircle = new Circle(54.5);
                    BRcircle.setStroke(Color.rgb(255, 255, 255));
                    BRcircle.setFill(Color.TRANSPARENT);
                    BRcircle.setCenterX(marbleIcon[i].getCenterX() + distance);
                    BRcircle.setCenterY(marbleIcon[i].getCenterY() + distance);
                    Panel.getChildren().add(BRcircle);
                    BRcircle.setOnMouseClicked(event -> {
                        Board[marbles[i].getBoardCode()] = false;
                        marbles[i].GoRightDown();
                        marbleIcon[i].setCenterX(BRcircle.getCenterX());
                        marbleIcon[i].setCenterY(BRcircle.getCenterY());
                        UpdateBoard(i, 0, false);
                        Turn = !Turn;
                        BRcircle.setVisible(false);
                        if (Turn)
                            WhoTurn.setText("White Turn");
                        else
                            WhoTurn.setText("Black Turn");
                    });
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    private void HitLeft(boolean color, int i) {
        if (color) {
            WLcircle = new Circle(54.5);
            WLcircle.setStroke(Color.rgb(255, 255, 255));
            WLcircle.setFill(Color.TRANSPARENT);
            WLcircle.setCenterX(marbleIcon[i].getCenterX() - 2 * distance);
            WLcircle.setCenterY(marbleIcon[i].getCenterY() - 2 * distance);
            Panel.getChildren().add(WLcircle);
            WLcircle.setOnMouseClicked(event -> {
                Board[marbles[i].getBoardCode()] = false;
                Board[marbles[i].getBoardCode() + 8 - 1] = false;
                int index = FindNode(marbles[i].getBoardCode() + 8 - 1);
                marbleIcon[index].setVisible(false);
                marbles[i].GoLeftUp();
                marbles[i].GoLeftUp();
                marbleIcon[i].setCenterX(WLcircle.getCenterX());
                marbleIcon[i].setCenterY(WLcircle.getCenterY());
                CountBlack--;
                cntBlack.setText(String.valueOf(CountBlack));
                Turn = !Turn;
                MustHit = false;
                WLcircle.setVisible(false);
                if (Turn)
                    WhoTurn.setText("White Turn");
                else
                    WhoTurn.setText("Black Turn");
                UpdateBoard(i, index, true);
            });
        } else {
            BLcircle = new Circle(54.5);
            BLcircle.setStroke(Color.rgb(255, 255, 255));
            BLcircle.setFill(Color.TRANSPARENT);
            BLcircle.setCenterX(marbleIcon[i].getCenterX() - 2 * distance);
            BLcircle.setCenterY(marbleIcon[i].getCenterY() + 2 * distance);
            Panel.getChildren().add(BLcircle);
            BLcircle.setOnMouseClicked(event -> {
                Board[marbles[i].getBoardCode()] = false;
                Board[marbles[i].getBoardCode() - 8 - 1] = false;
                int index = FindNode(marbles[i].getBoardCode() - 8 - 1);
                marbleIcon[index].setVisible(false);
                marbles[i].GoLeftDown();
                marbles[i].GoLeftDown();
                marbleIcon[i].setCenterX(BLcircle.getCenterX());
                marbleIcon[i].setCenterY(BLcircle.getCenterY());
                CountWhite--;
                cntWhite.setText(String.valueOf(CountWhite));
                Turn = !Turn;
                MustHit = false;
                if (Turn)
                    WhoTurn.setText("White Turn");
                else
                    WhoTurn.setText("Black Turn");
                UpdateBoard(i, index, true);
            });

        }
    }

    private void HitRight(boolean color, int i) {
        if (color) {
            WRcircle = new Circle(54.5);
            WRcircle.setStroke(Color.rgb(255, 255, 255));
            WRcircle.setFill(Color.TRANSPARENT);
            WRcircle.setCenterX(marbleIcon[i].getCenterX() + 2 * distance);
            WRcircle.setCenterY(marbleIcon[i].getCenterY() - 2 * distance);
            Panel.getChildren().add(WRcircle);
            WRcircle.setOnMouseClicked(event -> {
                Board[marbles[i].getBoardCode()] = false;
                Board[marbles[i].getBoardCode() + 8 + 1] = false;
                int index = FindNode(marbles[i].getBoardCode() + 8 + 1);
                marbleIcon[index].setVisible(false);
                marbles[i].GoRightUp();
                marbles[i].GoRightUp();
                marbleIcon[i].setCenterX(WRcircle.getCenterX());
                marbleIcon[i].setCenterY(WRcircle.getCenterY());
                CountBlack--;
                cntBlack.setText(String.valueOf(CountBlack));
                Turn = !Turn;
                MustHit = false;
                WRcircle.setVisible(false);
                if (Turn)
                    WhoTurn.setText("White Turn");
                else
                    WhoTurn.setText("Black Turn");
                UpdateBoard(i, index, true);
            });
        } else {
            BRcircle = new Circle(54.5);
            BRcircle.setStroke(Color.rgb(255, 255, 255));
            BRcircle.setFill(Color.TRANSPARENT);
            BRcircle.setCenterX(marbleIcon[i].getCenterX() + 2 * distance);
            BRcircle.setCenterY(marbleIcon[i].getCenterY() + 2 * distance);
            Panel.getChildren().add(BRcircle);
            BRcircle.setOnMouseClicked(event -> {
                Board[marbles[i].getBoardCode()] = false;
                Board[marbles[i].getBoardCode() - 8 + 1] = false;
                int index = FindNode(marbles[i].getBoardCode() - 8 + 1);
                marbleIcon[index].setVisible(false);
                marbles[i].GoRightDown();
                marbles[i].GoRightDown();
                marbleIcon[i].setCenterX(BRcircle.getCenterX());
                marbleIcon[i].setCenterY(BRcircle.getCenterY());
                CountWhite--;
                cntWhite.setText(String.valueOf(CountWhite));
                Turn = !Turn;
                MustHit = false;
                BRcircle.setVisible(false);
                if (Turn)
                    WhoTurn.setText("White Turn");
                else
                    WhoTurn.setText("Black Turn");
                UpdateBoard(i, index, true);
            });
        }
    }

    @Override
    public void handle(MouseEvent event) {
        if (Turn) {
            for (int i = 0; i < 12; i++) {
                if (event.getSource().equals(marbleIcon[i])) {
                    Winner();
                    Play(i);
                }
            }
        } else {
            for (int i = 12; i < 24; i++) {
                if (event.getSource().equals(marbleIcon[i])) {
                    Winner();
                    Play(i);
                }
            }
        }
    }

    private int FindNode(int code) {
        for (int i = 0; i < 24; i++) {
            if (marbles[i].getBoardCode() == code) {
                return i;
            }
        }
        return -1;
    }

    private void UpdateBoard(int i, int removeIndex, boolean hit) {
        Board[marbles[i].getBoardCode()] = true;
        if (isKing(i)) {
            if (marbles[i].Color.equals("White"))
                marbleIcon[i].setFill(Color.rgb(214, 189, 63));
            else
                marbleIcon[i].setFill(Color.rgb(154, 0, 0));
        }
        System.out.println(marbles[i].getBoardCode() + " true");
        if (hit) {
            marbleIcon[removeIndex].setVisible(false);
            System.out.println(removeIndex);
        }
//        Panel.getChildren().removeAll(marbleIcon);
//        Panel.getChildren().addAll(marbleIcon);
    }

    private boolean isKing(int index) {
        if (marbles[index].Color.equals("White")) {
            for (int i = 56; i < 64; i++) {
                if (marbles[index].getBoardCode() == i) {
                    marbles[index].setKing();
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < 8; i++) {
                if (marbles[index].getBoardCode() == i) {
                    marbles[index].setKing();
                    return true;
                }
            }
            return false;
        }
    }

    private void Winner() {
        if (CountWhite == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Black Wins");
            alert.showAndWait();
        } else if (CountBlack == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "White Wins");
            alert.showAndWait();
        }
    }

}
