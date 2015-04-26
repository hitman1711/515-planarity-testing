<h1>Planarity Testing</h1>
<h2>20091</h2>
<p></p>

&lt;hr&gt;


<h2>Contents</h2>
<ul>
<li> <a href='#general'> Goals </a> </li>
<li> <a href='#dates'> Dates </a> </li>
<li> <a href='#algorithms'> Algorithms </a> </li>
<li> <a href='#input'> Program Requirements </a> </li>
<li> <a href='#test'> Test Data </a> </li>
</ul> 

&lt;hr&gt;


<h2> <a> Goal </a> </h2>
<p> The goal of his project is to understand an implement and test a<br>
complex algorithm. Your grade will be 90 percent based on the<br>
correctness of your algorithm as evaluated by testing that I will do,<br>
and 10 percent on the clarity and efficiency of your code. </p>
<h2> <a> Dates </a> </h2>
<ul>
<li> You can do this project alone or with another student.</li>
<li> The program is due <strong><font color='red'>Wednesday of Week 11</font></strong>
You will submit it by emailing to me (as a single tar'ed, gzipped file) all<br>
source code that you use. You must include in each source file a<br>
comment listing the authors of all code you use, and except for code<br>
that is part of the standard java libraries, you and your partner must<br>
write all code. Make the subject line of your email read "Algorithms project."</li>
</ul>
<h2> <a> Algorithms </a> </h2>
<p> Implement the following algorithm (description taken from <a href='http://www.amazon.com/exec/obidos/tg/detail/-/0133016153/qid=1105503088/sr=8-1/ref=sr_8_xs_ap_i1_xgl14/103-6553315-8931810?v=glance&amp;s=books&amp;n=507846'>di Battista et al.</a>), which tests whether or not a graph is planar.</p>
<p> </p>

&lt;hr&gt;


<b>Planarity-Testing</b>

<i>Input:</i> a biconnected graph <i>G</i> with <i>n</i> vertices and a separating cycle <i>C</i> of <i>G</i>.<br>
<i>Output:</i> an indication of whether <i>G</i> is planar.<br>
<br>
<ol>
<li> If the graph has more than 3<i>n</i> -6 edges,<br>
return "nonplanar."</li>
<li> Compute the pieces of <i>G</i> with respect <i>C</i>.</li>
<li> For each piece <i>P</i> of <i>G</i> that is not a path,<br>
<ol>
<li> let <i>P'</i> be that graph obtained by adding <i>P</i> to <i>C</i> </li>
<li> let <i>C'</i> be the cycle of <i>P'</i> obtained from <i>C</i> by replacing the portion of <i>C</i> between two consecutive attachments with a path of <i>P</i> between them</li>
<li> apply the algorithm recursively to graph <i>P'</i> and cycle <i>C'</i>. If <i>P'</i> is nonplanar, return "nonplanar."</li>
</ol>
</li>
<li> Compute the interlacement graph <i>I</i> of the pieces.</li>
<li> Test whether <i>I</i> is bipartite. If <i>I</i> is not bipartite, return "nonplanar".</li>
<li> Return "planar."</li>
</ol>
<br>
<br>
<hr><br>
<br>
<h2> <a> Program Requirements </a> </h2>
<p> You must use Java and your code must run natively on the lab Sun machines.  Use good programming style. </p>
<p> Your program must be called <b>TestPlanarity</b> and should take a single argument: The name of the input file, which describes in the format given below a single, <b>biconnected</b> graph (you algorithm should only be given biconnected graphs). Your<br>
program will read this input file and run a planarity test on the graph it represents. If the graph is planar, your program must output (to standard output) "planar," otherwise it must output "nonplanar." The input file must be formatted as follows.</p>
<ul>
<li> Each line should contain exactly two integers separated by a space. </li>
<li> Each line represents an edge. </li>
<li> The two numbers on each line represent the vertices that are adjacent to the edge.</li>
</ul>
<p>Since I will be testing your code on my own test inputs, it<br>
is critical that your program can read precisely from the format<br>
described above.</p>
<h2> <a> Test Data</a> </h2>
<p>Since I will be grading your code on my own (secret, randomly<br>
generated) test cases, you need to carefully<br>
test your code to make sure that it works. You should leave as much<br>
time as possible for this phase. You are free to generate test data<br>
anyway you wish, but if you would like to automate the process, the<br>
following definitions are useful. The following is a recursive<br>
definition of a biconnected graph (not necessary planar or nonplanar).<br>
I will use these definitions to randomly generate my test cases.</p>
<ul>
<li><a>The triangle K_3 is a biconnected graph. </a></li>
<li><a>Any graph created by removing an edge {<i>u</i>, <i>v</i>} from a bidirected graph and adding a new vertex <i>w</i> and edges {<i>u</i>, <i>w</i>} and {<i>w</i>, <i>v</i>} to it is also biconnected. </a></li>
<li><a>Any graph created by adding a new vertex <i>w</i> and edges {<i>w</i>, <i>u</i>} and {<i>w</i>, <i>v</i>} to a bidirected graph that contains the vertices <i>u</i> and <i>v</i> is also biconnected.</a></li>
<li><a>Any graph created by adding a new edge {<i>u</i>, <i>v</i>} to a bidirected graph containing vertices <i>u</i> and <i>v</i> is also bidirected.</a></li>
</ul>
<a>Here is a recursive definition of a biconnected, nonplanar graph.</a>
<ul>
<li><a>K_5 and K<i>(3,3) are biconnected, nonplanar graphs. </a></li></i><li><a>Any graph created by removing an edge {<i>u</i>, <i>v</i>} from a bidirected, nonplanar graph and adding a new vertex <i>w</i> and edges {<i>u</i>, <i>w</i>} and {<i>w</i>, <i>v</i>} to it is also biconnected and nonplanar. </a></li>
<li><a>Any graph created by adding a new vertex <i>w</i> and edges {<i>w</i>, <i>u</i>} and {<i>w</i>, <i>v</i>} to a bidirected, nonplanar graph that contains the vertices <i>u</i> and <i>v</i> is also biconnected and nonplanar.</a></li>
<li><a>Any graph created by adding a new edge {<i>u</i>, <i>v</i>} to a bidirected, nonplanar graph containing vertices <i>u</i> and <i>v</i> is also bidirected and nonplanar.</a></li>
</ul>
<a>Here is a recursive definition of a biconnected, planar graph.</a>
<ul>
<li><a>The triangle K_3 is a biconnected, planar graph. </a></li>
<li><a>Any graph created by removing an edge {<i>u</i>, <i>v</i>} from a bidirected, planar graph and adding a new vertex <i>w</i> and edges {<i>u</i>, <i>w</i>}, {<i>w</i>, <i>v</i>} to it is also biconnected. </a></li>
<li><a>Any graph graph created by adding a new vertex <i>w</i> and edges {<i>w</i>, <i>u</i>} and {<i>w</i>, <i>v</i>} to a bidirected graph that contains a face containing the vertices <i>u</i> and <i>v</i> is also biconnected.</a></li>
<li><a>Any graph created by adding a new edge {<i>u</i>, <i>v</i>} to a bidirected graph that contains a face containing vertices <i>u</i> and <i>v</i> is also bidirected.</a></li>
</ul>