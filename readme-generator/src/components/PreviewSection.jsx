import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';

const PreviewSection = ({ markdown }) => {
  return (
    <div className="p-6">
      <div className="mb-4 pb-4 border-b border-gray-200">
        <h2 className="text-2xl font-bold text-gray-800 mb-2">👁️ Live Preview</h2>
        <p className="text-gray-600 text-sm">This is how your README will look</p>
      </div>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-8">
        <div className="markdown-preview prose prose-slate max-w-none">
          <ReactMarkdown
            remarkPlugins={[remarkGfm]}
            components={{
              // Custom rendering for code blocks
              code({ node, inline, className, children, ...props }) {
                const match = /language-(\w+)/.exec(className || '');
                return !inline ? (
                  <pre className="bg-gray-900 text-gray-100 p-4 rounded-lg overflow-x-auto">
                    <code className={className} {...props}>
                      {children}
                    </code>
                  </pre>
                ) : (
                  <code className="bg-gray-100 text-red-600 px-1.5 py-0.5 rounded text-sm" {...props}>
                    {children}
                  </code>
                );
              },
              // Custom rendering for links
              a({ node, children, href, ...props }) {
                return (
                  <a
                    href={href}
                    className="text-blue-600 hover:text-blue-800 underline"
                    target={href?.startsWith('http') ? '_blank' : undefined}
                    rel={href?.startsWith('http') ? 'noopener noreferrer' : undefined}
                    {...props}
                  >
                    {children}
                  </a>
                );
              },
              // Custom rendering for images
              img({ node, src, alt, ...props }) {
                return (
                  <img
                    src={src}
                    alt={alt}
                    className="max-w-full h-auto rounded-lg my-4 shadow-sm"
                    loading="lazy"
                    onError={(e) => {
                      e.target.style.display = 'none';
                      const parent = e.target.parentElement;
                      if (parent && !parent.querySelector('.image-placeholder')) {
                        const placeholder = document.createElement('div');
                        placeholder.className = 'image-placeholder bg-gray-200 rounded-lg p-8 text-center text-gray-500 my-4';
                        placeholder.innerHTML = `
                          <svg class="mx-auto h-12 w-12 text-gray-400 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                          </svg>
                          <p class="text-sm">Image: ${alt || src}</p>
                        `;
                        parent.appendChild(placeholder);
                      }
                    }}
                    {...props}
                  />
                );
              },
              // Custom rendering for tables
              table({ node, children, ...props }) {
                return (
                  <div className="overflow-x-auto my-6">
                    <table className="min-w-full border-collapse border border-gray-300" {...props}>
                      {children}
                    </table>
                  </div>
                );
              },
              th({ node, children, ...props }) {
                return (
                  <th className="border border-gray-300 bg-gray-100 px-4 py-2 text-left font-semibold" {...props}>
                    {children}
                  </th>
                );
              },
              td({ node, children, ...props }) {
                return (
                  <td className="border border-gray-300 px-4 py-2" {...props}>
                    {children}
                  </td>
                );
              },
              // Headings
              h1({ node, children, ...props }) {
                return <h1 className="text-4xl font-bold mb-4 mt-6 text-gray-900" {...props}>{children}</h1>;
              },
              h2({ node, children, ...props }) {
                return <h2 className="text-3xl font-bold mb-3 mt-5 text-gray-900" {...props}>{children}</h2>;
              },
              h3({ node, children, ...props }) {
                return <h3 className="text-2xl font-semibold mb-2 mt-4 text-gray-800" {...props}>{children}</h3>;
              },
              h4({ node, children, ...props }) {
                return <h4 className="text-xl font-semibold mb-2 mt-3 text-gray-800" {...props}>{children}</h4>;
              },
              // Lists
              ul({ node, children, ...props }) {
                return <ul className="list-disc list-inside my-3 ml-4 space-y-1" {...props}>{children}</ul>;
              },
              ol({ node, children, ...props }) {
                return <ol className="list-decimal list-inside my-3 ml-4 space-y-1" {...props}>{children}</ol>;
              },
              li({ node, children, ...props }) {
                return <li className="text-gray-700" {...props}>{children}</li>;
              },
              // Paragraphs
              p({ node, children, ...props }) {
                return <p className="my-3 text-gray-700 leading-relaxed" {...props}>{children}</p>;
              },
              // Blockquotes
              blockquote({ node, children, ...props }) {
                return (
                  <blockquote className="border-l-4 border-blue-500 pl-4 italic my-4 text-gray-600" {...props}>
                    {children}
                  </blockquote>
                );
              },
              // Horizontal rule
              hr({ node, ...props }) {
                return <hr className="my-6 border-t-2 border-gray-200" {...props} />;
              },
            }}
          >
            {markdown}
          </ReactMarkdown>
        </div>
      </div>

      {/* Raw Markdown View */}
      <div className="mt-6">
        <details className="bg-gray-100 rounded-lg">
          <summary className="cursor-pointer p-4 font-semibold text-gray-700 hover:bg-gray-200 rounded-lg transition-colors">
            📄 View Raw Markdown
          </summary>
          <div className="p-4">
            <pre className="bg-gray-900 text-gray-100 p-4 rounded-lg overflow-x-auto text-sm">
              <code>{markdown}</code>
            </pre>
          </div>
        </details>
      </div>
    </div>
  );
};

export default PreviewSection;
