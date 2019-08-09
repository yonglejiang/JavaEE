package com.cz.txl.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ImageCodeServlete
 */
public class ImageCodeServlete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageCodeServlete() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");//��ֵ���������ͼƬ��ʽ���н���
		response.setHeader("Prama", "No-cache");//��ֵͼƬ��ҳ���ϲ�����
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);//����ʧЧʱ��
		//��øôλỰ��session
		HttpSession session = request.getSession();
		
		//���ڴ��д���һ��ͼƬ
		int width = 100,height=40;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//��ȡͼ��������
		Graphics g = image.getGraphics();//��û���
		//����ͼƬ�ı���ɫ
		g.setColor(getRandColor(200, 250));//�ı仭����ɫ
		g.fillRect(0, 0, width, height);
		
		//�趨��֤������
		g.setFont(new Font("Times New Roman",Font.PLAIN,24));
		
		//���߿�
		g.setColor(getRandColor(160, 200));//�ı仭�ʵ���ɫ
		g.drawRect(0, 0, width-1, height-1);
		
		//���������
		Random random = new Random();
		
		//�����15�����ŵ�����,���ⱻ������ʶ�� OCR
		g.setColor(getRandColor(160, 200));//�ı仭�ʵ���ɫ
		for (int i = 0;i<15;i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			
			g.drawLine(x, y, x+xl, y+yl);
			
		}
		
		//������֤���ϵ������
		String sRand = "";
		for (int i=0;i<4;i++) {
			String rand = String.valueOf(random.nextInt(10));
			
			sRand += rand;
			
			//����֤����ʾ��ҳ����
			g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));//�ı仭����ɫ
			g.drawString(rand, 13*i + 14, 20);//�û��ʻ�����
		}
		
		//����֤�����ִ��뵽session��
		session.setAttribute("imageCode", sRand);
		
		//����ͼ��ʼ��Ч
		g.dispose();
		
		
		//����ͼƬ���뵽���ﷵ�ص��������
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	//������Χ�ڻ�������ɫ
	private Color getRandColor(int fc,int bc){
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		
		if (bc > 255) { 
			bc = 255;
		}
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		
		return new Color(r,g,b);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
