import java.util.Scanner;
public class oseroKai {

    //�Q�[�����s���t���O
    static boolean game = true;
    static final int MAP_SIZE = 8;

    //�I�Z���łɑΉ������������z��
    static String[][] board = new String[MAP_SIZE][MAP_SIZE];
    static String[][] copyBoard = new String[MAP_SIZE][MAP_SIZE];

    static final String EMPTY = "�@";
    static final String BLACK = "��";
    static final String WHITE = "��";

    static String stone;
    static String rev_stone;

    static int mode; //���ꃂ�[�h�����Ă����ϐ�

    static boolean count ;
    static public void initialize() {

        //�I�Z���ł̗v�f��S�ăN���A����
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                board[i][j] = EMPTY;

            }

        }

        //������Ԃ̔z�u
        board[3][3] = BLACK;
        board[3][4] = WHITE;
        board[4][3] = WHITE;
        board[4][4] = BLACK;
        //������̐F���w��
        stone = BLACK;
        rev_stone = WHITE;

        //�Q�[�����s���t���O
        game = true;

    }

    public static  void showBoard() {

        //�܂��󂢂Ă�����W�����邩
        boolean existempty = false; //�O��̂����Z�b�g
        //������̐��W�v�p
        int count_black = 0;
        //������̐��W�v�p
        int count_white = 0;



        //�I�Z���ł�`�ʂ���
        int i = 0;
        System.out.println("  |0 |1|2 |3|4 |5|6 |7 |"); //��ԏ�̕���
        System.out.println("�\�\�\�\�\�\�\�\�\�\�\�\�\�\�\"); //���̉��́\
        for (String[] sarr : board) { //�g��for��

            System.out.print(i + " |"); //�c�̋L�q
            for (String s : sarr) { //�g��for��

                System.out.print(s); //�z��̗v�f���o��
                System.out.print("|"); //�Ō��|

                //�󂢂Ă�����W�����邩�A�e��̏W�v
                switch (s) {
                    case EMPTY -> existempty = true; //�󂢂Ă�����ture
                    case BLACK -> count_black++;
                    case WHITE -> count_white++;
                }

            }
            System.out.println();
            System.out.println("�\�\�\�\�\�\�\�\�\�\�\�\�\�\");

            i++;

        }

        System.out.println(BLACK + ":" + count_black);
        System.out.println(WHITE + ":" + count_white);
        System.out.println("�\�\�\�\�\�\�\�\�\�\�\�\�\�\");

        if (count_black == 0 || count_white == 0) { //�󂪂����
            System.out.println(stone + "�Q�[���I���I");
            game = false;
        } else if (existempty) {
            System.out.println(stone + "�̃^�[���ł�");
        } else {
            System.out.println(stone + "�Q�[���I���I");
            game = false;
        }

    }

    public static void copyBoard(){ //board�̃R�s�[�����

        for (int i = 0; i < MAP_SIZE; i++){
            System.arraycopy(board[i], 0, copyBoard[i], 0, MAP_SIZE);
            for (int j = 0; j < MAP_SIZE; j++){ //�R�s�[board��\��t����
                System.arraycopy(board[j], 0, copyBoard[j], 0, MAP_SIZE);
            }
        }
    }

    public static void pasteBoard(){ //�R�s�[�ŏ㏑������

        for (int i = 0; i < MAP_SIZE; i++){
            System.arraycopy(copyBoard[i], 0, board[i], 0, MAP_SIZE);
            for (int j = 0; j < MAP_SIZE; j++){
                System.arraycopy(copyBoard[j], 0, board[j], 0, MAP_SIZE);
            }
        }
    }

    public static void reverseBoard(){ //�{�[�h�𔽓]����

        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (board[j][i].equals(BLACK)){
                    board[j][i] = WHITE;
                } else if (board[j][i].equals(WHITE)) {
                    board[j][i] = BLACK;
                }
            }
        }
    }
    public static void setStone(int x, int y) {

        // �ŊO�̍��W���w�肵���ꍇ
        if (x > 7 || y > 7 || x < 0 || y < 0){
            System.out.println("���̈ʒu�ɋ�͂����܂���");

        } else if (board[y][x].equals(EMPTY) ) { // ���z�u�ł���ꍇ
            copyBoard(); //�R�s�[������Ă���
            board[y][x] = stone; //�{�[�h�ɐ΂�u��
            // �Ђ�����Ԃ�����
            if (turnStone(x, y)) {
                System.out.print(board[6][0]);
                specialRule(x, y);//���ꃂ�[�h

                // ������̐ݒ� ������ւ�
                String next_rev_stone = stone;
                stone = rev_stone;
                rev_stone = next_rev_stone;
                // �I�Z���ł̕`��
                showBoard();
            }else {
                System.out.println("�����ւ���Ă��܂���");
                pasteBoard(); //�{�[�h���㏑��
            }

        } else {

            // ���ɋ�����Ă���ʒu���w�肵���ꍇ
            System.out.println("���̈ʒu�ɋ�͂����܂���"); //stone������ւ���ĂȂ�������v
        }

    }


    public static boolean turnStone(int x, int y) { //8�̃��\�b�h�ň�ł�true���A���Ă��������肵����

        // 8�����̋�̔z�u���m�F���A�Ђ�����Ԃ���
        boolean back = false; //�ǂ��false�Ȃ�false��Ԃ�

        if (turnUp(x,y)){ back = true; }

        if (turnDown(x,y)){ back = true; }

        if (turnRight(x,y)){back = true; }

        if (turnRightUp(x,y)){back = true; }

        if (turnRightDown(x,y)){ back = true; }

        if (turnLeft(x,y)){ back = true; }

        if (turnLeftUp(x,y)){ back = true; }

        if (turnLeftDown(x,y)){ back = true; }

        return back; //����Ԃ��Ă��邩�m�F

    }

    public static boolean turnLeftUp(int x, int y) {

        count = false;
        if (y > 1 && x > 1) {

            // �ƂȂ�̋�
            String next = board[y - 1][x - 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x - i < 0 || y - i < 0 || board[y - i][x - i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y - i][x - i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y - t][x - t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean turnUp(int x, int y) {

        count = false;
        if (y > 1) {

            // ��̋�
            String next = board[y - 1][x];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (y - i < 0 || board[y - i][x].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y - i][x].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y - t][x] = stone;
                        }
                        count = true;
                        break;
                    }

                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    public static boolean turnRightUp(int x, int y) {

        count = false;
        if (y > 1 && x < 6) {

            // �ƂȂ�̋�
            String next = board[y - 1][x + 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x + i > 7 || y - i < 0 || board[y - i][x + i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y - i][x + i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y - t][x + t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    public static boolean turnDown(int x, int y) {

        count = false;
        if (y < 6) {

            // �ƂȂ�̋�
            String next = board[y + 1][x];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (y + i > 7 || board[y + i][x].equals(EMPTY)) {

                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y + i][x].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y + t][x] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    public static boolean turnRight(int x, int y) {

        count = false;
        if (x < 6) {

            // �ƂȂ�̋�
            String next = board[y][x + 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x + i > 7 || board[y][x + i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y][x + i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y][x + t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }return false;
    }

    public static boolean turnLeftDown(int x, int y) {

        count = false;
        if (y < 6 && x > 1) {

            // �ƂȂ�̋�
            String next = board[y + 1][x - 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x - i < 0 || y + i > 7 || board[y + i][x - i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y + i][x - i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y + t][x - t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    public static boolean turnLeft(int x, int y) {

        count = false;
        if (x > 1) {

            // �ƂȂ�̋�
            String next = board[y][x - 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x - i < 0 || board[y][x - i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y][x - i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y][x - t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    public static boolean turnRightDown(int x, int y) {
        count = false;
        if (y < 6 && x < 6) {

            // �ƂȂ�̋�
            String next = board[y + 1][x + 1];

            // �ƂȂ�̋����̏ꍇ
            if (next.equals(rev_stone)) {

                // ����ɂ��̈�ƂȂ肩�珇�Ɋm�F
                for (int i = 2; true; i++) {

                    if (x + i > 7 || y + i > 7 || board[y + i][x + i].equals(EMPTY)) {
                        // ��Ȃ��ꍇ�I��
                        break;
                    } else if (board[y + i][x + i].equals(stone)) {
                        // ����̏ꍇ

                        // �������̋�����ׂĎ���ɂЂ����肩����
                        for (int t = 1; t < i; t++) {
                            // �z��̗v�f���㏑��
                            board[y + t][x + t] = stone;
                        }
                        count = true;
                        break;
                    }
                }
                return count;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }
    public static void specialRule(int x,int y){

        if (mode == 0){
            if ((x == 0 && y == 0) || (x == 7 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 7)) {
                System.out.println("�X�y�V�������[�h����");
                reverseBoard();
            }
        }
    }







        public static void main(String[] args){

            //�R���\�[������̓��͂��󂯕t����
            Scanner in = new Scanner(System.in);

            System.out.print("�X�y�V�������[����ON�ɂ��܂���?\n�[�̃}�X�ɒu���Ƃ��݂��̋����ւ��܂�(0:����A����ȊO:���Ȃ�)>");
            mode = in.nextInt();

            initialize();
            showBoard();

            //�Q�[�����s���t���O��true�̂��������[�v����
            while(game){

                System.out.print("�������x���W����͂��Ă�������:");
                int x = in.nextInt();

                System.out.print("�������y���W����͂��Ă�������:");
                int y = in.nextInt();

                setStone(x, y);

            }

            in.close();
        }
}
