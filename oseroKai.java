import java.util.Scanner;
public class oseroKai {

    //ゲーム実行中フラグ
    static boolean game = true;
    static final int MAP_SIZE = 8;

    //オセロ版に対応した多次元配列
    static String[][] board = new String[MAP_SIZE][MAP_SIZE];
    static String[][] copyBoard = new String[MAP_SIZE][MAP_SIZE];

    static final String EMPTY = "　";
    static final String BLACK = "＠";
    static final String WHITE = "○";

    static String stone;
    static String rev_stone;

    static int mode; //特殊モードを入れておく変数

    static boolean count ;
    static public void initialize() {

        //オセロ版の要素を全てクリアする
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                board[i][j] = EMPTY;

            }

        }

        //初期状態の配置
        board[3][3] = BLACK;
        board[3][4] = WHITE;
        board[4][3] = WHITE;
        board[4][4] = BLACK;
        //次うつ駒の色を指定
        stone = BLACK;
        rev_stone = WHITE;

        //ゲーム実行中フラグ
        game = true;

    }

    public static  void showBoard() {

        //まだ空いている座標があるか
        boolean existempty = false; //前回のをリセット
        //黒い駒の数集計用
        int count_black = 0;
        //白い駒の数集計用
        int count_white = 0;



        //オセロ版を描写する
        int i = 0;
        System.out.println("  |0 |1|2 |3|4 |5|6 |7 |"); //一番上の部分
        System.out.println("―――――――――――――――"); //その下の―
        for (String[] sarr : board) { //拡張for分

            System.out.print(i + " |"); //縦の記述
            for (String s : sarr) { //拡張for分

                System.out.print(s); //配列の要素を出力
                System.out.print("|"); //最後の|

                //空いている座標があるか、各駒数の集計
                switch (s) {
                    case EMPTY -> existempty = true; //空いていたらture
                    case BLACK -> count_black++;
                    case WHITE -> count_white++;
                }

            }
            System.out.println();
            System.out.println("――――――――――――――");

            i++;

        }

        System.out.println(BLACK + ":" + count_black);
        System.out.println(WHITE + ":" + count_white);
        System.out.println("――――――――――――――");

        if (count_black == 0 || count_white == 0) { //空があれば
            System.out.println(stone + "ゲーム終了！");
            game = false;
        } else if (existempty) {
            System.out.println(stone + "のターンです");
        } else {
            System.out.println(stone + "ゲーム終了！");
            game = false;
        }

    }

    public static void copyBoard(){ //boardのコピーを作る

        for (int i = 0; i < MAP_SIZE; i++){
            System.arraycopy(board[i], 0, copyBoard[i], 0, MAP_SIZE);
            for (int j = 0; j < MAP_SIZE; j++){ //コピーboardを貼り付ける
                System.arraycopy(board[j], 0, copyBoard[j], 0, MAP_SIZE);
            }
        }
    }

    public static void pasteBoard(){ //コピーで上書きする

        for (int i = 0; i < MAP_SIZE; i++){
            System.arraycopy(copyBoard[i], 0, board[i], 0, MAP_SIZE);
            for (int j = 0; j < MAP_SIZE; j++){
                System.arraycopy(copyBoard[j], 0, board[j], 0, MAP_SIZE);
            }
        }
    }

    public static void reverseBoard(){ //ボードを反転する

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

        // 版外の座標を指定した場合
        if (x > 7 || y > 7 || x < 0 || y < 0){
            System.out.println("その位置に駒はおけません");

        } else if (board[y][x].equals(EMPTY) ) { // 駒を配置できる場合
            copyBoard(); //コピーを作っておく
            board[y][x] = stone; //ボードに石を置く
            // ひっくり返す処理
            if (turnStone(x, y)) {
                System.out.print(board[6][0]);
                specialRule(x, y);//特殊モード

                // 次うつ駒の設定 駒を入れ替え
                String next_rev_stone = stone;
                stone = rev_stone;
                rev_stone = next_rev_stone;
                // オセロ版の描写
                showBoard();
            }else {
                System.out.println("駒が入れ替わっていません");
                pasteBoard(); //ボードを上書き
            }

        } else {

            // 既に駒がおいてある位置を指定した場合
            System.out.println("その位置に駒はおけません"); //stoneが入れ替わってないから大丈夫
        }

    }


    public static boolean turnStone(int x, int y) { //8個のメソッドで一つでもtrueが帰ってきたか判定したい

        // 8方向の駒の配置を確認し、ひっくり返えす
        boolean back = false; //どれもfalseならfalseを返す

        if (turnUp(x,y)){ back = true; }

        if (turnDown(x,y)){ back = true; }

        if (turnRight(x,y)){back = true; }

        if (turnRightUp(x,y)){back = true; }

        if (turnRightDown(x,y)){ back = true; }

        if (turnLeft(x,y)){ back = true; }

        if (turnLeftUp(x,y)){ back = true; }

        if (turnLeftDown(x,y)){ back = true; }

        return back; //駒が裏返っているか確認

    }

    public static boolean turnLeftUp(int x, int y) {

        count = false;
        if (y > 1 && x > 1) {

            // となりの駒
            String next = board[y - 1][x - 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x - i < 0 || y - i < 0 || board[y - i][x - i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y - i][x - i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // 上の駒
            String next = board[y - 1][x];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (y - i < 0 || board[y - i][x].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y - i][x].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y - 1][x + 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x + i > 7 || y - i < 0 || board[y - i][x + i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y - i][x + i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y + 1][x];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (y + i > 7 || board[y + i][x].equals(EMPTY)) {

                        // 駒がない場合終了
                        break;
                    } else if (board[y + i][x].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y][x + 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x + i > 7 || board[y][x + i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y][x + i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y + 1][x - 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x - i < 0 || y + i > 7 || board[y + i][x - i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y + i][x - i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y][x - 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x - i < 0 || board[y][x - i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y][x - i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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

            // となりの駒
            String next = board[y + 1][x + 1];

            // となりの駒が裏駒の場合
            if (next.equals(rev_stone)) {

                // さらにその一つとなりから順に確認
                for (int i = 2; true; i++) {

                    if (x + i > 7 || y + i > 7 || board[y + i][x + i].equals(EMPTY)) {
                        // 駒がない場合終了
                        break;
                    } else if (board[y + i][x + i].equals(stone)) {
                        // 自駒の場合

                        // あいだの駒をすべて自駒にひっくりかえす
                        for (int t = 1; t < i; t++) {
                            // 配列の要素を上書き
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
                System.out.println("スペシャルモード発動");
                reverseBoard();
            }
        }
    }







        public static void main(String[] args){

            //コンソールからの入力を受け付ける
            Scanner in = new Scanner(System.in);

            System.out.print("スペシャルルールをONにしますか?\n端のマスに置くとお互いの駒が入れ替わります(0:する、それ以外:しない)>");
            mode = in.nextInt();

            initialize();
            showBoard();

            //ゲーム実行中フラグがtrueのあいだループする
            while(game){

                System.out.print("駒をおくx座標を入力してください:");
                int x = in.nextInt();

                System.out.print("駒をおくy座標を入力してください:");
                int y = in.nextInt();

                setStone(x, y);

            }

            in.close();
        }
}
