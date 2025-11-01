import { useState } from 'react'
import FormSection from './components/FormSection'
import PreviewSection from './components/PreviewSection'
import { generateMarkdown, defaultData } from './utils/generateMarkdown'

function App() {
  const [formData, setFormData] = useState(defaultData);
  const [markdown, setMarkdown] = useState(generateMarkdown(defaultData));

  const handleDataChange = (newData) => {
    setFormData(newData);
    setMarkdown(generateMarkdown(newData));
  };

  const handleDownload = () => {
    const blob = new Blob([markdown], { type: 'text/markdown' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'README.md';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  return (
    <div className="h-screen flex flex-col bg-gray-50">
      {/* Header */}
      <header className="bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-lg">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold">📝 README Generator</h1>
            <p className="text-sm text-blue-100">Create beautiful README files effortlessly</p>
          </div>
          <button
            onClick={handleDownload}
            className="bg-white text-blue-600 px-6 py-2 rounded-lg font-semibold hover:bg-blue-50 transition-colors shadow-md flex items-center gap-2"
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
            </svg>
            Download README.md
          </button>
        </div>
      </header>

      {/* Main Content */}
      <div className="flex-1 flex overflow-hidden">
        {/* Left Side - Form */}
        <div className="w-1/2 overflow-y-auto border-r border-gray-200 bg-white">
          <FormSection data={formData} onChange={handleDataChange} />
        </div>

        {/* Right Side - Preview */}
        <div className="w-1/2 overflow-y-auto bg-gray-50">
          <PreviewSection markdown={markdown} />
        </div>
      </div>
    </div>
  )
}

export default App
