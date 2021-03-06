import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		String TokenNameDict [] = {"EOF",
			"LPAREN",
			"RPAREN",
			"LBRACK",
			"RBRACK",
			"LBRACE",
			"RBRACE",
			"NIL",
			"PLUS",
			"MINUS",
			"TIMES",
			"DIVIDE",
			"COMMA",
			"DOT",
			"SEMICOLON",
			"TYPE_INT",
			"ASSIGN",
			"EQ",
			"LT",
			"GT",
			"ARRAY",
			"CLASS",
			"EXTENDS",
			"RETURN",
			"WHILE",
			"IF",
			"NEW",
			"INT",
			"STRING",
			"ID",
			"TYPE_STRING"};
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				/************************/
				/* [6] Print to console */
				/************************/
				System.out.print(TokenNameDict[s.sym]);
				if ( s.value != null ) {
				System.out.print("(");
				System.out.print(s.value);
				System.out.print(")");
				}
				System.out.print("[");
				System.out.print(l.getLine());
				System.out.print(",");
				System.out.print(l.getTokenStartPosition());
				System.out.print("]");
				System.out.print("\n");
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				file_writer.print(TokenNameDict[s.sym]);
				if ( s.value != null ) {
				file_writer.print("(");
				file_writer.print(s.value);
				file_writer.print(")");
				}
				file_writer.print("[");
				file_writer.print(l.getLine());
				file_writer.print(",");
				file_writer.print(l.getTokenStartPosition());
				file_writer.print("]");
				file_writer.print("\n");
				
				/***********************/
				/* [8] Read next token */
				/***********************/
				try {
					s = l.next_token();
				} catch (Exception e) {
					/* Encountered errornoues token */
					System.out.println("ERROR: " + e.getMessage());
					file_writer.close();
					file_writer = new PrintWriter(outputFilename);
					file_writer.print("ERROR");
					break;
				}
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


